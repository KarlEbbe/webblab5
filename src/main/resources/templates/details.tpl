<!DOCTYPE html>
<html>
  <head>
    <title>Details on {{ unicorn.name }}</title>
    <link rel='stylesheet' href='/static/style.css' />
    <link href='http://fonts.googleapis.com/css?family=Great+Vibes' rel='stylesheet' type='text/css'>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
  </head>
  <body>
    <h1>{{ name }}</h1> 
    <article>
      <section id="description">
        <blockquote>
          <p>{{ description }}</p>
        </blockquote>
        <div id="metadata">
          <div id="reportedBy">{{ reportedBy }}, </div>
          <div id="reportedWhen">{{ spottedWhen }}</div>
        </div>
      </section>
      <section id="image">
        <a href="{{image }}"><img alt="unicorn #{{ id }}" src="{{image }}"></a>
      </section>
      <section id="location">
        Sågs i {{location }} (lat {{ lat }}, lon {{ lon }})
      </section>
    </article>
  </body>
</html>
