/*
* Copyright 2012 Benjamin Glatzel <benjamin.glatzel@me.com>
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.terasology.editor.properties;

import org.terasology.game.CoreRegistry;
import org.terasology.rendering.renderingProcesses.DefaultRenderingProcess;
import org.terasology.rendering.world.Skysphere;
import org.terasology.rendering.world.WorldRenderer;

import java.util.List;

/**
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class SceneProperties implements IPropertyProvider {
    @Override
    public void addPropertiesToList(List<Property> properties) {
        Skysphere skysphere = CoreRegistry.get(WorldRenderer.class).getSkysphere();
        if (skysphere != null) {
            skysphere.addPropertiesToList(properties);
        }
        DefaultRenderingProcess postRenderer = DefaultRenderingProcess.getInstance();
        if (postRenderer != null) {
            postRenderer.addPropertiesToList(properties);
        }
    }
}
