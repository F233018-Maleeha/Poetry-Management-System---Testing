import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.IncludeTags;

@Suite
@SelectPackages({"BusinessLayer", "DTOPkg", "DataAccessLayer"})
@IncludeTags("fast")
public class FastTestSuite {
    // Runs only fast unit tests
}
