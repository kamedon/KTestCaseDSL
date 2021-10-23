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

## 4. output TestCase

Output in `any format` you want.

### ex: output Markdown

Sample Markdown Extension

```kotlin
fun TestSuite.markdown(): String {
   // ...
}

suite.markdown()
```

- [TestCaseTest.kt](https://github.com/kamedon/KTestCaseDSL/blob/master/src/commonTest/kotlin/com.kamedon.ktestcase/TestCaseTest.kt)

output text

```
## case1
### 事前条件
- pre-condition-1

1. step1-1

2. step2-2
    - [ ]  verify1-2-1

3. step1-3
    - [ ]  verify1-3-1
    - [ ]  verify1-3-2

4. step1-4
    - [ ]  verify1-4-1
### 期待結果
    - [ ]  verify-1
    - [ ]  verify-2
### 事後条件条件
- post-condition-1
- post-condition-2

## case2
### 事前条件

1. step2-1
    - [ ]  verify2-1-1
### 期待結果
### 事後条件条件
```
