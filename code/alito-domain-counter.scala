import org.warcbase.spark.matchbox._
import org.warcbase.spark.rdd.RecordRDD._

val r = RecordLoader.loadArchives("/collections/webarchives/scotus/alito/*.arc.gz", sc) 
.keepValidPages()
.map(r => r.getUrl)
.countItems()
.saveAsTextFile("alito-domains")

