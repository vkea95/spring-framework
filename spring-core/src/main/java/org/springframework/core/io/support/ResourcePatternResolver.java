/*
 * Copyright 2002-2007 the original author or authors.
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

package org.springframework.core.io.support;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 策略接口用来解决一个位置模板（举例：一个ant风格路径模式）进入资源对象们
 * Strategy interface for resolving a location pattern (for example,
 * an Ant-style path pattern) into Resource objects.。
 * 这是一个对org.springframework.core.io.ResourceLoader接口的拓展。
 * 一个传入资源加载器可以被检查是否它实现了这个被拓展的接口。
 * <p>This is an extension to the {@link org.springframework.core.io.ResourceLoader}
 * interface. A passed-in ResourceLoader (for example, an
 * {@link org.springframework.context.ApplicationContext} passed in via
 * {@link org.springframework.context.ResourceLoaderAware} when running in a context)
 * can be checked whether it implements this extended interface too.
 *
 * <p>{@link PathMatchingResourcePatternResolver} is a standalone implementation
 * that is usable outside an ApplicationContext, also used by
 * {@link ResourceArrayPropertyEditor} for populating Resource array bean properties.
 *
 * <p>Can be used with any sort of location pattern (e.g. "/WEB-INF/*-context.xml"):
 * Input patterns have to match the strategy implementation. This interface just
 * specifies the conversion method rather than a specific pattern format.
 *
 * <p>This interface also suggests a new resource prefix "classpath*:" for all
 * matching resources from the class path. Note that the resource location is
 * expected to be a path without placeholders in this case (e.g. "/beans.xml");
 * JAR files or classes directories can contain multiple files of the same name.
 *
 * ResourceLoader 的 Resource getResource(String location) 方法，
 * 每次只能根据 location 返回一个 Resource 。当需要加载多个资源时，
 * 我们除了多次调用 #getResource(String location) 方法外，别无他法。
 * org.springframework.core.io.support.ResourcePatternResolver 是
 * ResourceLoader 的扩展，它支持根据指定的资源路径匹配模式每次返回多个 Resource 实例，
 * 其定义如下：
 *
 * @author Juergen Hoeller
 * @since 1.0.2
 * @see org.springframework.core.io.Resource
 * @see org.springframework.core.io.ResourceLoader
 * @see org.springframework.context.ApplicationContext
 * @see org.springframework.context.ResourceLoaderAware
 */
public interface ResourcePatternResolver extends ResourceLoader {

	/**
	 * 也新增了一种新的协议前缀 "classpath*:"，该协议前缀由其子类负责实现。
	 * 假URL前缀为所有匹配的资源---那些来自类路径： "classpath*:"
	 * 这个不同于ResourceLoader's classpath URL prefix，在那里，它获取所有匹配资源--根据制定名字，
	 * 例如在所有被部署的文件的根部
	 * Pseudo URL prefix for all matching resources from the class path: "classpath*:"
	 * This differs from ResourceLoader's classpath URL prefix in that it
	 * retrieves all matching resources for a given name (e.g. "/beans.xml"),
	 * for example in the root of all deployed JAR files.
	 * @see org.springframework.core.io.ResourceLoader#CLASSPATH_URL_PREFIX
	 */
	String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

	/**
	 * 这个方法和上面的那个成员变量是不同于DefaultResourceLoader的地方
	 * 以支持根据路径匹配模式返回多个 Resource 实例。
	 * 解决被给定的位置模式进入资源对象中。
	 * 重叠的资源entry----指向了同样的物理资源，这些entry应该被避免，asap。
	 * 结果该设置语义
	 * Resolve the given location pattern into Resource objects.
	 * <p>Overlapping resource entries that point to the same physical
	 * resource should be avoided, as far as possible. The result should
	 * have set semantics.
	 * @param locationPattern the location pattern to resolve
	 * @return the corresponding Resource objects
	 * @throws IOException in case of I/O errors
	 */
	Resource[] getResources(String locationPattern) throws IOException;

}
