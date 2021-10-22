# generate TestCase with Kotlin

[![Java CI with Gradle](https://github.com/kamedon/KTestCaseDSL/actions/workflows/gradle.yml/badge.svg)](https://github.com/kamedon/KTestCaseDSL/actions/workflows/gradle.yml)

## Usage

### 1. add maven Repository to `build.gradle.kts`

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/Kamedon/KTestCaseDSL")
        credentials {
            username = "USER_NAME" 
            password = "USER PERSONAL ACCESS TOKEN"
        }
    }
}
```

### 2. add dependencies to `build.gradle.kts`
```kotlin
dependencies {
    implementation("com.kamedon:ktestcasedsl:x.y.z")
}
```


### 3. define TestCase

```kotlin
class TestCaseTest {
    @Test
    fun suiteTest() {
        val suite = suite("TestSuite 1") {
            case("case1") {
                step("step1-1")
                step("step2-2") {
                    verify("verify1-2-1")
                }
                step("step3") {
                    verify("verify1-3-1")
                    verify("verify1-3-2")
                }
                step("step1-4") {
                    verify("verify1-4-1")
                }
            }
            case("case2") {
                step("step2-1") {
                    verify("verify2-1-1")
                }
            }
        }
        assertEquals(suite.title, "TestSuite 1")
        assertEquals(suite.cases[0].title, "case1")
        assertEquals(suite.cases[0].caseSteps[1].title, "step2-2")
        assertEquals(suite.cases[0].caseSteps[2].verifies[1].title, "verify1-3-2")
        assertEquals(suite.cases[1].caseSteps[0].verifies[0].title, "verify2-1-1")
    }
}

```

## 4. Output TestCase

Output in `any format` you want.

### ex: output Markdown

```kotlin
fun TestSuite.markdown(): String {
    fun TestCase.title() = "### ${title}\n"
    fun TestCaseStep.title(index: Int) = "${index}. ${title}\n"
    fun TestCaseStepVerify.title() = "    - [ ]  $title"

    val out = cases.map { case ->
        case.title() + case.caseSteps.mapIndexed { index, caseStep ->
            caseStep.title(index + 1) + caseStep.verifies.joinToString("\n") { caseStepVerify ->
                caseStepVerify.title()
            }
        }.joinToString("\n")
    }.joinToString("\n")

    return out
}
```

```kotlin
suite.markdown()
```

output text

```
### case1
1. step1-1

2. step2-2
    - [ ]  verify1-2-1
3. step3
    - [ ]  verify1-3-1
    - [ ]  verify1-3-2
4. step1-4
    - [ ]  verify1-4-1
### case2
1. step2-1
    - [ ]  verify2-1-1
```
