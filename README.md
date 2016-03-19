# NewsPlus [![Build Status](https://ci.wertarbyte.com/job/NewsPlus/badge/icon)](https://ci.wertarbyte.com/job/NewsPlus)
A news plugin for Bukkit that supports RSS feeds and various display methods.

## Installation
Just [download the jar][ci] and put it in your plugin directory. Once you restart your server, a default
config will be created.

[ci]: https://ci.wertarbyte.com/job/NewsPlus/lastStableBuild/

The default config contains demo news feed. It's easy to adapt it for your needs. You can configure
as many news sources as you want.

```yaml
news:
- type: "rss" #only RSS feeds are supported for now
  url: "http://example.com/feed/" #URL of your RSS feed
  format: "&f[&6NEWS&f] {{title}}" #format of news broadcast
  interval: 10 #interval (in minutes) to broadcast the news
  count: 3 #number of articles to broadcast every time
  sendOnJoin: false #whether to send the news to players when they join (defaults to false)
```

The following placeholders are available for the `format` option:

| Placeholder   | Description                    |
|---------------|--------------------------------|
| `{{title}}`   | the title of the news entry    |
| `{{link}}`    | the link to the news entry     |
| `{{content}}` | the content of the news entry  |

You don't really need the `{{link}}` placeholder though as players can simply click on broadcasted
news to open the link in their browsers.


## Planned features
For now, broadcasting the news to all players is the only option.
Later, it will be possible to put news on posters and signs.

## License
NewsPlus is licensed under the MIT license, see the [license file][license].

[license]: https://github.com/leMaik/NewsPlus/blob/master/LICENSE