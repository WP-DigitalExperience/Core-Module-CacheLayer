/**
 * webTools-contentengine
 * Copyright (C) 2016  Thorsten Marx (kontakt@thorstenmarx.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.thorstenmarx.webtools.core.modules.cachelayer.module;

import com.thorstenmarx.modules.api.ModuleLifeCycleExtension;
import com.thorstenmarx.modules.api.annotation.Extension;
import com.thorstenmarx.webtools.api.CoreModuleContext;
import com.thorstenmarx.webtools.api.cache.CacheLayer;
import com.thorstenmarx.webtools.api.cluster.ClusterService;
import com.thorstenmarx.webtools.core.modules.cachelayer.ClusterCacheLayer;
import com.thorstenmarx.webtools.core.modules.cachelayer.LocalCacheLayer;

/**
 *
 * @author marx
 */
@Extension(ModuleLifeCycleExtension.class)
public class CoreModuleCacheLayerModuleLifeCycle extends ModuleLifeCycleExtension {

	public static CacheLayer cachelayer;

	private CoreModuleContext getCoreContext() {
		return (CoreModuleContext) getContext();
	}

	@Override
	public void activate() {
		
		if (getContext().serviceRegistry().exists(ClusterService.class)) {
			cachelayer = new ClusterCacheLayer(getContext().serviceRegistry().single(ClusterService.class).get());
		} else {
			cachelayer = new LocalCacheLayer();
		}

		getContext().serviceRegistry().register(CacheLayer.class, cachelayer);
	}

	@Override
	public void deactivate() {
		getContext().serviceRegistry().unregister(CacheLayer.class, cachelayer);
	}

	@Override
	public void init() {

	}

}
