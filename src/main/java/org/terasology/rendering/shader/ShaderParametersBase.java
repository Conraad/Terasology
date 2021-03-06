/*
 * Copyright 2012 Benjamin Glatzel <benjamin.glatzel@me.com>
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
package org.terasology.rendering.shader;

import org.terasology.config.Config;
import org.terasology.editor.properties.IPropertyProvider;
import org.terasology.editor.properties.Property;
import org.terasology.game.CoreRegistry;
import org.terasology.rendering.assets.GLSLShaderProgramInstance;
import org.terasology.rendering.world.WorldRenderer;
import org.terasology.world.WorldProvider;

import javax.vecmath.Vector3f;
import java.util.List;

/**
 * Basic shader parameters for all shader program.
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class ShaderParametersBase implements IPropertyProvider, IShaderParameters {

    public ShaderParametersBase() {
    }

    @Override
    public void applyParameters(GLSLShaderProgramInstance program) {
        program.setFloat("viewingDistance", CoreRegistry.get(Config.class).getRendering().getActiveViewingDistance() * 8.0f);

        WorldRenderer worldRenderer = CoreRegistry.get(WorldRenderer.class);
        WorldProvider worldProvider = CoreRegistry.get(WorldProvider.class);

        if (worldRenderer != null) {
            program.setFloat("daylight", (float) worldRenderer.getDaylight());
            program.setFloat("swimming", worldRenderer.isUnderWater() ? 1.0f : 0.0f);
            program.setFloat("tick", (float) worldRenderer.getTick());
            program.setFloat("sunlightValueAtPlayerPos", worldRenderer.getSmoothedPlayerSunlightValue());

            if (worldRenderer.getActiveCamera() != null) {
                final Vector3f cameraDir = worldRenderer.getActiveCamera().getViewingDirection();
                final Vector3f cameraPosition = worldRenderer.getActiveCamera().getPosition();

                program.setFloat3("cameraPosition", cameraPosition.x, cameraPosition.y, cameraPosition.z);
                program.setFloat3("cameraDirection", cameraDir.x, cameraDir.y, cameraDir.z);
                program.setFloat3("cameraParameters", worldRenderer.getActiveCamera().getzNear(), worldRenderer.getActiveCamera().getzFar(), 0.0f);
            }

            Vector3f sunDirection = worldRenderer.getSkysphere().getSunDirection(false);
            program.setFloat3("sunVec", sunDirection.x, sunDirection.y, sunDirection.z);
        }

        if (worldProvider != null) {
            program.setFloat("time", worldProvider.getTimeInDays());
        }
    }

    @Override
    public void addPropertiesToList(List<Property> properties) {
    }
}
