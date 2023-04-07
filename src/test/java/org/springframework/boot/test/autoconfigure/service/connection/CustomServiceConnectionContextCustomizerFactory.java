/*
 * Copyright 2012-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.test.autoconfigure.service.connection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.testcontainers.containers.GenericContainer;

import org.springframework.boot.autoconfigure.service.connection.ConnectionDetails;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * {@link ContextCustomizerFactory} to support service connections in tests.
 *
 * @author Moritz Halbritter
 * @author Andy Wilkinson
 * @author Phillip Webb
 */
public class CustomServiceConnectionContextCustomizerFactory implements ContextCustomizerFactory {

	@Override
	public ContextCustomizer createContextCustomizer(Class<?> testClass,
			List<ContextConfigurationAttributes> configAttributes) {
		List<ContainerConnectionSource<?, ?, ?>> sources = new ArrayList<>();
		collectContainerConnectionSources(testClass, sources);

		// look into classes provided through @ContextConfiguration annotation
		for (ContextConfigurationAttributes configAttribute : configAttributes) {
			for (Class<?> aClass : configAttribute.getClasses()) {
				collectContainerConnectionSources(aClass, sources);
			}
		}

		return (sources.isEmpty()) ? null : new CustomServiceConnectionContextCustomizer(sources);
	}

	private void collectContainerConnectionSources(Class<?> testClass, List<ContainerConnectionSource<?, ?, ?>> sources) {
		ReflectionUtils.doWithFields(testClass, (field) -> {
			MergedAnnotations annotations = MergedAnnotations.from(field);
			annotations.stream(ServiceConnection.class)
					.forEach((annotation) -> sources.add(createSource(field, annotation)));
		});
	}

	private ContainerConnectionSource<?, ?, ?> createSource(Field field,
			MergedAnnotation<ServiceConnection> annotation) {
		Class<? extends ConnectionDetails> connectionDetailsType = getConnectionDetailsType(annotation);
		Object fieldValue = getFieldValue(field);
		Assert.isInstanceOf(GenericContainer.class, fieldValue,
				"Field %s must be a %s".formatted(field.getName(), GenericContainer.class.getName()));
		GenericContainer<?> container = (GenericContainer<?>) fieldValue;
		return new ContainerConnectionSource<>(connectionDetailsType, field, annotation, container);
	}

	@SuppressWarnings("unchecked")
	private Class<? extends ConnectionDetails> getConnectionDetailsType(
			MergedAnnotation<ServiceConnection> annotation) {
		return (Class<? extends ConnectionDetails>) annotation.getClass(MergedAnnotation.VALUE);
	}

	private Object getFieldValue(Field field) {
		ReflectionUtils.makeAccessible(field);
		return ReflectionUtils.getField(field, null);
	}

}
