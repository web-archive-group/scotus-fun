import org.warcbase.spark.matchbox._
import org.warcbase.spark.rdd.RecordRDD._
import org.warcbase.spark.matchbox.{ExtractDomain, ExtractLinks, RecordLoader, WriteGDF, RemoveHTML}

val r = RecordLoader.loadArchives("/collections/webarchives/scotus/roberts/*.arc.gz", sc) 
.keepValidPages()
.map(r => r.getUrl)
.countItems()
.saveAsTextFile("roberts-domains")

RecordLoader.loadArchives("/collections/webarchives/scotus/roberts/*.arc.gz", sc)
  .keepValidPages()
  .map(r => (r.getCrawlDate, ExtractLinks(r.getUrl, r.getContentString)))
  .flatMap(r => r._2.map(f => (r._1, ExtractDomain(f._1).replaceAll("^\\s*www\\.", ""), ExtractDomain(f._2).replaceAll("^\\s*www\\.", ""))))
  .filter(r => r._2 != "" && r._3 != "")
  .countItems()
  .filter(r => r._2 > 5)
  .saveAsTextFile("roberts.sitelinks")

val links = RecordLoader.loadArchives("/collections/webarchives/scotus/roberts/*.arc.gz", sc)
  .keepValidPages()
  .map(r => (r.getCrawlDate, ExtractLinks(r.getUrl, r.getContentString)))
  .flatMap(r => r._2.map(f => (r._1, ExtractDomain(f._1).replaceAll("^\\s*www\\.", ""), ExtractDomain(f._2).replaceAll("^\\s*www\\.", ""))))
  .filter(r => r._2 != "" && r._3 != "")
  .countItems()
  .filter(r => r._2 > 5)

WriteGDF(links, "roberts.sitelinks.gdf")

RecordLoader.loadArchives("/collections/webarchives/scotus/roberts/*.arc.gz", sc)
  .keepValidPages()
  .map(r => (r.getCrawlDate, r.getDomain, r.getUrl, RemoveHTML(r.getContentString)))
  .saveAsTextFile("roberts-plaintext")
