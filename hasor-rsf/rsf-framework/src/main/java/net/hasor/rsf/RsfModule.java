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
package net.hasor.rsf;
import net.hasor.core.ApiBinder;
import net.hasor.core.Module;

/**
 * Rsf 制定 Hasor Module。
 * @version : 2014年11月12日
 * @author 赵永春 (zyc@hasor.net)
 */
@FunctionalInterface
public interface RsfModule extends Module {
    public default void loadModule(final ApiBinder apiBinder) throws Throwable {
        RsfApiBinder rsfApiBinder = apiBinder.tryCast(RsfApiBinder.class);
        if (rsfApiBinder == null) {
            return;
        }
        this.loadModule(rsfApiBinder);
    }

    public void loadModule(RsfApiBinder apiBinder) throws Throwable;
}