/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.web.context;
import net.hasor.core.ApiBinder;
import net.hasor.core.Provider;
import net.hasor.core.container.BeanContainer;
import net.hasor.core.context.DataContextCreater;
import net.hasor.core.context.StatusAppContext;
import net.hasor.web.ServletVersion;
import net.hasor.web.WebAppContext;
import net.hasor.web.WebEnvironment;
import net.hasor.web.binder.FilterPipeline;
import net.hasor.web.binder.ListenerPipeline;
import net.hasor.web.binder.support.ManagedFilterPipeline;
import net.hasor.web.binder.support.ManagedListenerPipeline;
import net.hasor.web.binder.support.ManagedServletPipeline;

import javax.servlet.ServletContext;
/**
 *
 * @version : 2013-7-16
 * @author 赵永春 (zyc@hasor.net)
 */
public class WebTemplateAppContext<C extends BeanContainer> extends StatusAppContext<C> implements WebAppContext {
    private ServletContext servletContext = null;
    public WebTemplateAppContext(WebEnvironment environment, C container) {
        super(environment, container);
        this.servletContext = environment.getServletContext();
    }
    public WebTemplateAppContext(WebEnvironment environment, DataContextCreater<C> creater) throws Throwable {
        super(environment, creater);
        this.servletContext = environment.getServletContext();
    }
    //
    @Override
    public WebEnvironment getEnvironment() {
        return (WebEnvironment) super.getEnvironment();
    }
    /**获取{@link ServletContext}*/
    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }
    /**获取容器目前支持的 Servet Api 版本。*/
    @Override
    public ServletVersion getServletVersion() {
        return this.getEnvironment().getServletVersion();
    }
    /**当完成所有初始化过程之后调用，负责向 Context 绑定一些预先定义的类型。*/
    protected void doBind(final ApiBinder apiBinder) {
        super.doBind(apiBinder);
        final WebAppContext appContet = this;
        //
        /*绑定Environment对象的Provider*/
        apiBinder.bindType(WebEnvironment.class).toProvider(new Provider<WebEnvironment>() {
            public WebEnvironment get() {
                return appContet.getEnvironment();
            }
        });
        /*绑定AppContext对象的Provider*/
        apiBinder.bindType(WebAppContext.class).toProvider(new Provider<WebAppContext>() {
            public WebAppContext get() {
                return appContet;
            }
        });
        /*绑定ServletContext对象的Provider*/
        apiBinder.bindType(ServletContext.class).toProvider(new Provider<ServletContext>() {
            public ServletContext get() {
                return getServletContext();
            }
        });
        /*绑定当前Servlet支持的版本*/
        apiBinder.bindType(ServletVersion.class).toProvider(new Provider<ServletVersion>() {
            public ServletVersion get() {
                return appContet.getEnvironment().getServletVersion();
            }
        });
        //
    }
}