package io.github.ovso.globaltrend

import android.util.Xml.Encoding
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import org.junit.Test
import java.net.URLEncoder

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//  @Test
//  fun addition_isCorrect() {
//    assertEquals(4, 2 + 2)
//  }

  @Test
  fun test_jsoup() {
    Thread {
      val get = Jsoup.connect("https://trends.google.co.kr/trends/trendingsearches/daily/rss")
        .data("geo", URLEncoder.encode("KR", Encoding.UTF_8.name))
        .parser(Parser.xmlParser())
        .timeout(3000)
        .get()

      println(get.toString())
    }.start()
  }
}
