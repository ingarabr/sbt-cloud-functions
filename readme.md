# sbt-gcp-function
Make it simple to test and deploy a GCP function by leveraging the sbt-plugin ecosystem.

## Usage

Add the plugin to `projects/plugins.sbt`

```
addSbtPlugin("com.github.ingarabr" % "sbt-gcp-functions" % "<version>>")
```

The plugin has the following tasks:

| Task | Description | 
|---|---|
| `gcpFunctionRunLocally` | Run the function locally. The default port is 8080 | 
| `gcpFunctionDeploy` | Deploy the function to the configured environment. | 


## Configuration

The plugin requires some information about the target environment and the function artifact. 

The example below depends on [sbt-assembly]. You can change that to any other sbt-plugin that can create a fat-jar. 

```sbt
enablePlugins(GcpFunctionsPlugin)

// The function class name.
gcpFunctionClass := "my.package.HelloFunction"

// The jar to deploy. Example using sbt-assembly
gcpFunctionJar := assembly.value

// Environment deployment configuration.
gcpFunctionDeployConfiguration := DeployConfiguration(
  functionName = "my-function",
  gcpProject = "my-google-project",
  gcpLocation = "us-central1",
  memoryMb = 512, // default value
  triggerHttp = true, // default value
  allowUnauthenticated = false, // default value
  runtime = "java11" // default value
)

// The port used when testing the function locally.
gcpFunctionPort := 8080 // default value

// Additional config. Usually not needed to change.
// Specify versions.
// gcpFunctionInvokerVersion := ""
// gcpFunctionFrameworkApiVersion "= ""
```

## Limitations and improvements
- Replace gcloud cli with google jars to make it easier to use.
- Add support to deploy without making a fat-jar.
- Autodetect the function class if it's only one.
- Add verification of the function size limitation before deploying.


[sbt-assembly]: https://github.com/sbt/sbt-assembly