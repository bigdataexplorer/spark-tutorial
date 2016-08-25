package wsc.bigdata.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

/**
 * Created by weishungchung on 8/24/16.
 */
object AnalyzeTitanicData {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Analyze Stock Data")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)

    val inputPath = args(0)
    val data = sqlContext.read.format("com.databricks.spark.csv").option("header","true").option("inferSchema","true").load(inputPath)

    //perform aggregation group by gender
    println("group by gender")
    val groupByGender = data.groupBy("Sex").count().sort(desc("count"))
    groupByGender.show()

    //group by survived
    println("group by survival")
    val groupBySurvived = data.groupBy("Survived").count().sort(desc("count"))
    groupBySurvived.show()

    //group by gender, survival
    val groupByGenderSurvived = data.groupBy("Sex","Survived").count().sort(asc("Sex"), desc("count"))
    groupByGenderSurvived.show()
  }
}