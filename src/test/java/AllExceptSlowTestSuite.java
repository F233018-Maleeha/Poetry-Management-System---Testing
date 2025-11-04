import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.ExcludeTags;

@Suite
@SelectPackages({"BusinessLayer", "DTOPkg", "DataAccessLayer"})
@ExcludeTags("slow")
public class AllExceptSlowTestSuite {
    // Runs all tests except slow ones
}