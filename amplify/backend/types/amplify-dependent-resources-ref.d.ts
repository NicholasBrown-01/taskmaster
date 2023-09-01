export type AmplifyDependentResourcesAttributes = {
  "analytics": {
    "taskmaster": {
      "Id": "string",
      "Region": "string",
      "appName": "string"
    }
  },
  "api": {
    "taskmaster": {
      "GraphQLAPIEndpointOutput": "string",
      "GraphQLAPIIdOutput": "string",
      "GraphQLAPIKeyOutput": "string"
    }
  },
  "auth": {
    "taskmasterbbe3e380": {
      "AppClientID": "string",
      "AppClientIDWeb": "string",
      "IdentityPoolId": "string",
      "IdentityPoolName": "string",
      "UserPoolArn": "string",
      "UserPoolId": "string",
      "UserPoolName": "string"
    }
  },
  "predictions": {
    "speechGenerator480d8d7e": {
      "language": "string",
      "region": "string",
      "voice": "string"
    },
    "translateTextbce4ea8a": {
      "region": "string",
      "sourceLang": "string",
      "targetLang": "string"
    }
  },
  "storage": {
    "taskmasterStorage": {
      "BucketName": "string",
      "Region": "string"
    }
  }
}