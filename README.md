## 📱 Currency Converter App — Ejercicio Práctico de API con Retrofit, Flow y ViewModel

Una aplicación Android que convierte de EUR  a otras monedas usando una API pública. Ideal como práctica para aprender a:

* Consumir APIs REST con Retrofit
* Manejar respuestas con corrutinas y Flow
* Organizar tu código con MVVM
* Usar Moshi para parsear JSON dinámico
* Mostrar estados (Loading, Error, Success) en la UI de forma reactiva

---

### 🛠️ Tecnologías Usadas

* **Kotlin**
* **Retrofit + Moshi** para el consumo de APIs
* **StateFlow + Coroutines** para flujo reactivo
* **MVVM** como arquitectura base
* **ViewModel + Factory** para manejo de lógica de UI
* **Material Design / ViewBinding**

---

### 🚀 ¿Qué hace esta app?

* Permite ingresar una cantidad en una moneda origen.
* Selecciona una moneda destino.
* Convierte y muestra el valor actualizado en base a la tasa de cambio actual.
* Muestra errores en caso de fallos de red o datos inválidos.
* Usa un diseño simple pero funcional.

---

### ⟳ Flujo de trabajo

1. **MainActivity** → Captura los inputs del usuario y escucha los estados desde el ViewModel.
2. **CurrencyViewModel** → Maneja la lógica de conversión y actualiza el `StateFlow` con Loading, Success o Error.
3. **MainInterfaceImpl** → Interactúa con la API y devuelve una respuesta segura usando una clase `Resource`.
4. **RetrofitClient** → Configura Retrofit + Moshi con logging HTTP.
5. **CurrencyApi** → Define el endpoint de la API de ExchangeRates.

---

### 💡 Trucos y aprendizajes clave

* El JSON puede tener claves dinámicas, como las tasas (`rates`). Se puede parsear directamente como `Map<String, Double>`.
* Se usa `Moshi` con `KotlinJsonAdapterFactory` para adaptar JSON a Kotlin.
* El uso de `StateFlow` permite observar cambios reactivos en la UI sin LiveData.
* Se implementa un `sealed class` para representar distintos estados de la conversión de manera elegante.

---

### 📦 Cómo correr el proyecto

1. Clona el repositorio.
2. Agrega tu clave de API de [ExchangeRatesAPI](https://apilayer.com/marketplace/exchangerates_data-api) en `ApiConfig.kt` como `API_KEY`.
3. Ejecuta el proyecto en un emulador o dispositivo físico Android.

---

### 🧠 Para seguir aprendiendo

* Agrega testing unitario al `ViewModel` con `Turbine` o `Mockito`.
* Implementa Room para guardar tasas offline.
* Agrega soporte para múltiples idiomas o temas oscuros.
* Migra a Jetpack Compose para una UI declarativa.
