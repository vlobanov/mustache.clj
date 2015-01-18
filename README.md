# mustache.clj - Logic-less {{mustache}} templates for Clojure

This is a fork of [mustache.clj](https://github.com/shenfeng/mustache.clj)
with nested lookups (see example)

### Installation

```clj
[me.vlobanov/mustache "1.3"]
```

Example:

```
<!DOCTYPE html>
<html>
  <head>
    <title>{{ data.en.title }}</title>
  </head>
  <body>
    <ul>
      {{#data.en.list}}<li>id: {{id}}, name: {{name}}</li>{{/data.en.list}}
    </ul>
  </body>
</html>
```
with data
```clj
  {:data 
     {:en 
        {:title "mustache.clj - Logic-less {{mustache}} templates for Clojure"
         :list ({:id 1, :name "name1"}
                {:id 2, :name "name2"}
                {:id 3, :name "name3"})}}} ```
Outputs:

```
<!DOCTYPE html>
<html>
  <head>
    <title>mustache.clj - Logic-less {{mustache}} templates for Clojure</title>
  </head>
  <body>
    <ul>
      <li>id: 1, name: name1</li><li>id: 2, name: name2</li><li>id: 3, name: name3</li>
    </ul>
  </body>
</html>
```

So to have I18n support you just need to read YAML file as a hash and assoc it to data under `:lang` key.

Conditional output (`{{?data.t}}true{{/data.t}}{{^data.t}}false{{/data.t}}` => `true`) is also supported.

Please refer to [mustache.clj](https://github.com/shenfeng/mustache.clj) for full documentation.
