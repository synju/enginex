#version 400 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toCameraVector;
in float visibility;
in vec3 toLightVector[14];

uniform vec3 lightColor[14];
uniform vec3 attenuation[14];
uniform sampler2D modelTexture;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;
uniform int maxLights;

out vec4 out_Color;

void main(void) {
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVectorToCamera = normalize(toCameraVector);

	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);

	for(int i = 0; i < maxLights; i++) {
		float distance = length(toLightVector[i]);
		float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(toLightVector[i]);
		float nDot1 = dot(unitNormal,unitLightVector);
		float brightness = max(nDot1, 0.0);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		totalDiffuse = totalDiffuse + (brightness * lightColor[i]) / attFactor;
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i]) / attFactor;
	}
	totalDiffuse = max(totalDiffuse, 0.2);

	// This doesn't affect the code!!!
	vec4 textureColor = texture(modelTexture, pass_textureCoordinates);
	if(textureColor.a<0.5) {
		discard;
	}

	out_Color = vec4(totalDiffuse,1.0) * texture(modelTexture,pass_textureCoordinates) + vec4(totalSpecular, 1.0);
	out_Color = mix(vec4(skyColor,1.0), out_Color, visibility);
}