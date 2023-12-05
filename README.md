### Hexlet tests and linter status:


<div align="center">
  
| [![Actions Status](https://github.com/stanmoskalenko/java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/stanmoskalenko/java-project-71/actions) | [![Tests](https://github.com/stanmoskalenko/java-project-71/actions/workflows/main.yml/badge.svg)](https://github.com/stanmoskalenko/java-project-71/actions) | [![Maintainability](https://api.codeclimate.com/v1/badges/2d18e3f0fc9c14a825ee/maintainability)](https://codeclimate.com/github/stanmoskalenko/java-project-71/maintainability)|[![Test Coverage](https://api.codeclimate.com/v1/badges/2d18e3f0fc9c14a825ee/test_coverage)](https://codeclimate.com/github/stanmoskalenko/java-project-71/test_coverage) |
|---|---|---| ---|

</div>

# Summary

Difference Calculator is designed for comparing files. Extensions are supported: 
* `YAML`;
* `JSON`.

The result is a description of what has been changed in the file. The following output formats are currently supported: 
* `STYLISH`;
* `PLAIN`;
* `JSON`.

<details>
<summary>Stylish format example</summary>

```plain
Property 'chars2' was updated. From [complex value] to false
Property 'checked' was updated. From false to true
Property 'default' was updated. From null to [complex value]
Property 'id' was updated. From 45 to null
Property 'key1' was removed
Property 'key2' was added with value: 'value2'
Property 'numbers2' was updated. From [complex value] to [complex value]
Property 'numbers3' was removed
Property 'numbers4' was added with value: [complex value]
Property 'obj1' was added with value: [complex value]
Property 'setting1' was updated. From 'Some value' to 'Another value'
Property 'setting2' was updated. From 200 to 300
Property 'setting3' was updated. From true to 'none'
```

</details>

<details>
<summary>Plain format example</summary>

```plain
{
    chars1: [a, b, c]
  - chars2: [d, e, f]
  + chars2: false
  - checked: false
  + checked: true
  - default: null
  + default: [value1, value2]
  - id: 45
  + id: null
  - key1: value1
  + key2: value2
    numbers1: [1, 2, 3, 4]
  - numbers2: [2, 3, 4, 5]
  + numbers2: [22, 33, 44, 55]
  - numbers3: [3, 4, 5]
  + numbers4: [4, 5, 6]
  + obj1: {nestedKey=value, isNested=true}
  - setting1: Some value
  + setting1: Another value
  - setting2: 200
  + setting2: 300
  - setting3: true
  + setting3: none
}
```

</details>

<details>
<summary>Json format example</summary>

```json
{
  "result": [
    {
      "newValue": [
        "a",
        "b",
        "c"
      ],
      "type": "unmodified",
      "key": "chars1"
    },
    {
      "newValue": false,
      "oldValue": [
        "d",
        "e",
        "f"
      ],
      "type": "modified",
      "key": "chars2"
    },
    {
      "newValue": true,
      "oldValue": false,
      "type": "modified",
      "key": "checked"
    },
    {
      "newValue": [
        "value1",
        "value2"
      ],
      "oldValue": "null",
      "type": "modified",
      "key": "default"
    },
    {
      "newValue": "null",
      "oldValue": 45,
      "type": "modified",
      "key": "id"
    },
    {
      "oldValue": "value1",
      "type": "deleted",
      "key": "key1"
    },
    {
      "newValue": "value2",
      "type": "added",
      "key": "key2"
    },
    {
      "newValue": [
        1,
        2,
        3,
        4
      ],
      "type": "unmodified",
      "key": "numbers1"
    },
    {
      "newValue": [
        22,
        33,
        44,
        55
      ],
      "oldValue": [
        2,
        3,
        4,
        5
      ],
      "type": "modified",
      "key": "numbers2"
    },
    {
      "oldValue": [
        3,
        4,
        5
      ],
      "type": "deleted",
      "key": "numbers3"
    },
    {
      "newValue": [
        4,
        5,
        6
      ],
      "type": "added",
      "key": "numbers4"
    },
    {
      "newValue": {
        "nestedKey": "value",
        "isNested": true
      },
      "type": "added",
      "key": "obj1"
    },
    {
      "newValue": "Another value",
      "oldValue": "Some value",
      "type": "modified",
      "key": "setting1"
    },
    {
      "newValue": 300,
      "oldValue": 200,
      "type": "modified",
      "key": "setting2"
    },
    {
      "newValue": "none",
      "oldValue": true,
      "type": "modified",
      "key": "setting3"
    }
  ]
}
```

</details>

# Usage 

Build differ: 
```bash
make build
```

Compare content from command line: 
```bash
app -f plain json/1.json json/2.json
```

Compare the use of content via method calls. Use `Differ.generate`: 
```java
public String exampleMethod() throws IOException {
    return Differ.generate("/resources/first.yaml", "/resources/second.yaml", "plain");
}
```

# Usage Example

[![asciicast](https://asciinema.org/a/dUwNotSPJxlaxSKc6zMaklvsi.svg)](https://asciinema.org/a/dUwNotSPJxlaxSKc6zMaklvsi)


