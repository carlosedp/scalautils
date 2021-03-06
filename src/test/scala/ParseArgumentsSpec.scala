import com.carlosedp.scalautils.ParseArguments
import org.scalatest.flatspec._
import org.scalatest.matchers.should._

class ParseArgumentsSpec extends AnyFlatSpec with Matchers {

  "ParseArguments" should "parse one parameter" in {
    val args                    = Array("-param1", "data1", "generated", "singleparam1")
    val (params, remainingargs) = ParseArguments(args, List("param1", "param2"))

    params should be(Map("param1" -> "data1"))
    remainingargs should be(List("generated", "singleparam1"))
  }

  it should "parse multiple parameters" in {
    val args                    = Array("-param1", "data1", "generated", "-param2", "data2", "-anotherparam")
    val (params, remainingargs) = ParseArguments(args, List("param1", "param2"))

    params should be(Map("param1" -> "data1", "param2" -> "data2"))
    remainingargs should be(List("generated", "-anotherparam"))
  }

  it should "parse one parameter and ignore a wrong one" in {
    val args                    = Array("-param1", "-wrong", "generated", "-anotherparam", "mydata", "-param2", "data2")
    val (params, remainingargs) = ParseArguments(args, List("param1", "param2"))

    params should be(Map("param2" -> "data2"))
    remainingargs should be(List("-wrong", "generated", "-anotherparam", "mydata"))
  }

  it should "parse one parameter with double dash('--')" in {
    val args                    = Array("--param1", "data1", "generated", "--anotherparam", "mydata", "-param2", "data2")
    val (params, remainingargs) = ParseArguments(args, List("param1", "param2"), "--")

    params should be(Map("param1" -> "data1"))
    remainingargs should be(List("generated", "--anotherparam", "mydata", "-param2", "data2"))
  }

  it should "parse one parameter with single dash and pass double dash ones" in {
    val args                    = Array("-param1", "data1", "generated", "--anotherparam", "mydata", "-param2", "data2")
    val (params, remainingargs) = ParseArguments(args, List("param1"), "-")

    params should be(Map("param1" -> "data1"))
    remainingargs should be(List("generated", "--anotherparam", "mydata", "-param2", "data2"))
  }
}
