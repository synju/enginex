#version 400 core

in vec3 position;
in vec2 textureCoordinates;
in vec3 normals;

uniform vec3 lightPosition[14];
uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform float numberOfRows;
uniform vec2 offset;
uniform float density;
uniform float gradient;
uniform float useFakeLighting;
uniform int currentLightCount;

out vec3 toLightVector[14];
out vec3 surfaceNormal;
out vec3 toCameraVector;
out vec2 pass_textureCoordinates;
out float visibility;

void main(void) {
	vec4 worldPosition = transformationMatrix * vec4(position,1.0);
	vec4 positionRelativeToCam = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * positionRelativeToCam;
	pass_textureCoordinates = (textureCoordinates/numberOfRows) + offset;

	vec3 actualNormal = normals;
	if(useFakeLighting > 0.5) {
		actualNormal = vec3(0.0,1.0,0.0);
	}

	surfaceNormal = (transformationMatrix * vec4(actualNormal, 0.0)).xyz;
	for(int i = 0; i < currentLightCount; i++) {
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}

	toCameraVector = (inverse(viewMatrix) * vec4(0.0/0.0/0.0/1.0)).xyz - worldPosition.xyz;

	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance*density),gradient));
	visibility = clamp(visibility, 0.0,1.0);
}