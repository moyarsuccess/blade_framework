[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.moyar/blade/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.moyar/blade)

# Blade Dependency injection framework for Android

A dependency injection framework for Android platform.

## Setup

Gradle / Groovy
```groovy
implementation 'dev.moyar:blade:1.0.0'
```

```kotlin
implmentation("dev.moyar:blade:1.0.0")
```

## Usage

For moving forward with the examples first, we need to declare a sample class like below:

```kotlin
// Interface decleration
interface HttpClient {
  suspend fun execute(httpRequest: HttpRequest)
}
// Class decleration
class DefaultHttpClient: HttpClient {
  override suspend fun execute(httpRequest: HttpRequest) {}
}
```

### How to define a Blade module

```kotlin
val networkModule = module {
  // declarations of the dependencies' providers go here
}
```

### How to provide a simple instance

```kotlin
val networkModule = module {
  factory { DefaultHtttClient() }
}
```

But this factory always returns a DefaultHttpClient, not a HttpClient interface instance. How do we provide the interface instead?

```kotlin
val networkModule = module {
  factory<HttpClient> { DefaultHtttClient() }
}
```

### What if the DefaultHttpClient needs a base URL?

```kotlin
// Class decleration
class DefaultHttpClient(private val baseUrl: String): HttpClient {
  override suspend fun execute(httpRequest: HttpRequest) {}
}
```

In this case, we need to provide the base URL.

```kotlin
val networkModule = module {

  factory { "https://fw.tv/api" }

  factory<HttpClient> { 
    DefaultHtttClient(baseUrl = get()) 
  }
}
```

The Blade provides the ***get()*** function to provide a dependency from the graph.

### What if the DefaultHttpClient gets a parameter that is nullable? And it’s possible that no one already provides that.

In this case, you need just use getOrNull() which provides a nullable version of the object and not throw and exception.

```kotlin
val networkModule = module {

  factory<HttpClient> { 
    DefaultHtttClient(baseUrl = getOrNull()) 
  }
}
```

### What if the DefaultHttpClient gets two strings as the parameter?

```kotlin
// Class decleration
class DefaultHttpClient(
  private val baseUrl: String,
  private val userId: String,
): HttpClient {
  override suspend fun execute(httpRequest: HttpRequest) {}
}
```

In this case, we need to provide each string with a different ***qualifier.***

```kotlin
const val BASE_URL_QUALIFIER = "BASE_URL_QUALIFIER"
const val USER_ID_QUALIFIER = "USER_ID_QUALIFIER"

val networkModule = module {

  factory(qualifer = BASE_URL_QUALIFIER) { "https://fw.tv/api" }
  
  factory(qualifier = USER_ID_QUALIFIER) { "user_id" }

  factory<HttpClient> { 
    DefaultHtttClient(
      baseUrl = get(qualifier = BASE_URL_QUALIFIER),
      userId = get(qualifier = USER_ID_QUALIFIER)
    ) 
  }
}
```

### How to start Blade with the globally scoped modules

```kotlin
fun main() {
  // Load only one module 
  startBlade(networkModule)
  
  // Load a vararg of modules
  startBlade(networkModule, playerModule, ...)
}
```

Every single module that is added in at the start time will be added to the global scope.

### Scopes

For having scopes in Blade you need to define your scope like below:

```kotlin
fun activityScope(scopeComponentId: String) = scope(scopeComponentId) {
  module {
    factory { /* Provide your scoped dependencies here */ }
    single { /* Provide your scoped dependencies as scoped singletons here */ }
  }
}
```

Each scope itself internally has a scope Id, that the developer does not touch. If we want to pass a scope as a parent scope to another ScopeComponent to be the child of this scope. We will see this in the following examples.

### Scope Component

Now the question rises is how to manage the lifecycle of these scopes. The ScopeComponent is basically the owner of the scope, it means the Android framework's main components can be a ScopeComponent, like what? Like: Activity, Fragment, View, and so on. However, since the ScopeComponent is just an interface everything technically can be a ScopeComponent. Let’s see how an activity for instance can be a scope component.

```kotlin
val mainActivityScope: DiScope 
  get() = scope(scopeComponentId) {
    module {
      factory { /* Provide your scoped dependencies here */ }
      single { /* Provide your scoped dependencies as scoped singletons here */ }
    }
}

class MainActivity: ScopeAwareActivity() {

  override val scope: mainActivityScope
  
  override fun onCreate(...) {
    super.onCreate(...)
    setContentView(...)
    ...
  }

}
```

As you can see in the above example, the only thing you need to do is just implement the ScopeAwareActivity class and provide an instance of the scope is the scope of the component. Blade manages the entire lifecycle of the scope that is now tied to the lifecycle of the MainActivity.

### Sub-Scoping

Another need that is required sometimes is sub-scoping, which means sometimes some pages are inside others and the developer needs to provide smaller scopes for inner ones while the inner scopes have access to the graph of their parent scopes. In the example above let’s assume that MainActivity provides some instances of some classes but in one of its fragments we need to have our own scope to provide other instances, while if there is anything that provides that is above our knowledge there can be provided by MainActivity scope or even its parent until eventually, global scopes can provide that.

```kotlin
val framentScope: DiScope 
  get() = scope(scopeComponentId) {
    module {
      factory { /* Provide your scoped dependencies here */ }
      single { /* Provide your scoped dependencies as scoped singletons here */ }
    }
}

class SubScopeFragment: ScopeAwareFragment() {

  override val scope = framentScope
  
  override fun onCreateView(...) {
    super.onCreate(...)
    return ...
  }
  
  companion object {

    fun newInstance(parentScopeId: String): SubScopeFragment {
      return SubScopeFragment().apply {
        arguments = Bundle().apply {
          putExtra(DI_PARENT_SCOPE_ID_KEY, parentScopeId)
        }
      }
    }
  }
}

// Now when you create this fragment can pass the scope id of the parent scope like this
SubScopeFragment.newInstance(scope.scopeId)

// If you are already in the MainActivity class then
SubScopeFragment.newInstance(scope.scopeId)
```

In this solution, sub-scoping happens naturally and does not need to be managed manually, especially since the living and dying of the scopes happen automatically and also resolving an instance of a class happens automatically upward. This means if the inner scope can not find an instance it asks its super scope and if super scopes can not provide it asks its super scopes until finally global scope can provide that.

### How to inject an instance of the HttpClient interface?

```kotlin
fun main() {
  val httpClient: HttpClient = inject()
}
```

Keep in mind that inject function only searches the global scope, otherwise, the inject can only happen in ScopeComponent.

### How to inject from inside of a scope

```kotlin
class MainActivity: ScopeAwareActivity() {

    // Use lazyInject()
    val httpClient: HttpClient by lazyInject()
    
    override fun onCreate(...) {
        super.onCreate(...)
        // Normal inject
        val httpClient: HttpClient = inject()
        
        // Also can use injectOrNull
        val httpClient: HttpClient = injectOrNull()
    }
    
}
```

In Above example the `HttpClient` will be provided from the scope first if the scope has not the knowledge how to provide the instance, it will ask its super scopes until global scope, so finally some scope knows how to provide it.

### Let’s suppose we have another implementation of the HttpClient interface for test purposes. How we can provide it? and how we can inject it.

```kotlin
// Class decleration
class TesttHttpClient: HttpClient {
  override suspend fun execute(httpRequest: HttpRequest) {}
}
const val BASE_URL_QUALIFIER = "BASE_URL_QUALIFIER"
const val USER_ID_QUALIFIER = "USER_ID_QUALIFIER"

const val DEFAULT_HTTP_QUALIFIER = "DEFAULT_HTTP_QUALIFIER"
const val TEST_HTTP_QUALIFIER = "TEST_HTTP_QUALIFIER"

val networkModule = module {

  factory(qualifer = BASE_URL_QUALIFIER) { "https://fw.tv/api" }
  
  factory(qualifier = USER_ID_QUALIFIER) { "user_id" }

  factory<HttpClient>(qualifier = DEFAULT_HTTP_QUALIFIER) { 
    DefaultHtttClient(
      baseUrl = get(qualifier = BASE_URL_QUALIFIER),
      userId = get(qualifier = USER_ID_QUALIFIER)
    ) 
  }
  
  factory<HttpClient>(qualifier = TEST_HTTP_QUALIFIER) { TestHttpClient() }
}
fun main() {
  val httpClient: HttpClient = inject(qualifier = DEFAULT_HTTP_QUALIFIER)
}
```

### How to inject lazily an instance?

```kotlin
fun main() {
  val httpClient: HttpClient = lazyInject()
  
  // Your codes go here
  // . . .
  
  // Right here the value will be actually injected.
  httpClient.execute()
}
```

### What if a parameter for the injected instance can only be provided at injection time? (Assisted injection)

Let’s suppose the userId that the DefaultHttpClient needs are provided only at the time of the injection.

This is how we create our module:

```kotlin
const val BASE_URL_QUALIFIER = "BASE_URL_QUALIFIER"

const val DEFAULT_HTTP_QUALIFIER = "DEFAULT_HTTP_QUALIFIER"
const val TEST_HTTP_QUALIFIER = "TEST_HTTP_QUALIFIER"

val networkModule = module {

  factory(qualifer = BASE_URL_QUALIFIER) { "https://fw.tv/api" }

  factory<HttpClient>(qualifier = DEFAULT_HTTP_QUALIFIER) { parameters ->
    DefaultHtttClient(
      baseUrl = get(qualifier = BASE_URL_QUALIFIER),
      userId = parameters.get()
    )
  }
  
  factory<HttpClient>(qualifier = TEST_HTTP_QUALIFIER) { TestHttpClient() }
}
```

As you see above the parameter for ***userId*** is getting from the parameter object in the factory DSL. Now let’s see how to provide that during the *injection time*.

```kotlin
fun main() {
  val httpClient: HttpClient = inject(paramsHolder = parametersOf(DiParameter("user_id")))
}
```

***DiParameter*** also gets the ***qualifier*** for the time that we want to pass two or more values of the same time to the parameters for assisted injection.

Also, the ***get()*** function of the parameter object in the factory or single DSL gets these qualifier values. like below:

```kotlin
const val BASE_URL_QUALIFIER = "BASE_URL_QUALIFIER"
const val USER_ID_QUALIFIER = "USER_ID_QUALIFIER"

const val DEFAULT_HTTP_QUALIFIER = "DEFAULT_HTTP_QUALIFIER"
const val TEST_HTTP_QUALIFIER = "TEST_HTTP_QUALIFIER"

val networkModule = module {

  factory<HttpClient>(qualifier = DEFAULT_HTTP_QUALIFIER) { parameters ->
    DefaultHtttClient(
      baseUrl = parameters.get(qualifier = BASE_URL_QUALIFIER),
      userId = parameters.get(qualifier = USER_ID_QUALIFIER)
    )
  }
  
  factory<HttpClient>(qualifier = TEST_HTTP_QUALIFIER) { TestHttpClient() }
}
fun main() {
  val httpClient: HttpClient = inject(
    paramsHolder = parametersOf(
      DiParameter(entry = "user_id", qualifier = USER_ID_QUALIFIER),
      DiParameter(entry = "https://fw.tv/api", qualifier = BASE_URL_QUALIFIER)
    )
  )
}
```

## Provide a *singleton* instance of a dependency

```kotlin
val networkModule = module {
  single { DefaultHtttClient() }
}
```

If you use single instead of the factory then the Blade provides the same instance each time of injection. All other abilities above work for ***single*** as ***same*** as ***factory***.

### What if we are not sure whether a value is being provided by the Blade framework or not?

There are some cases in which we are not sure of the existence of an instance, for example, the case that there is an optional callback from the Host app in SDK. In this case, we can use ***injectOrNull()***

```kotlin
fun main() {
  val callback: Calback? = injectOrNull()
}
```
