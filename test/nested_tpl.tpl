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
