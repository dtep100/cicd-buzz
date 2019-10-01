import pytest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import subprocess
import signal
import urllib3
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)



# Local tests needing a web browser that should not run on the pipeline
class TestBrowserBehaviour:

    _use_remote_server = False

    @classmethod
    def setup_class(cls):
        """ setup any state specific to the execution of the given class (which
        usually contains tests).
        """
        if not cls._use_remote_server:
            cls._launch_local_server()
            cls.driver = webdriver.Safari()
        else:
            # The command_executor tells the test to run on Sauce, while the desired_capabilities
            # parameter tells us which browsers and OS to spin up.
            desired_cap = {
                'platform': "Mac OS X 10.13",
                'browserName': "safari",
                'version': "11.1",
                'build': "CI/CD Buzz build pipeline",
                'name': "TestBrowserBehaviour tests",
            }
            username = "dtep100"  # "SAUCE_USERNAME"
            access_key = "1387ea2b-9f2f-4238-8111-56b835051648"  # "SAUCE_ACCESS_KEY"
            cls.driver = webdriver.Remote(
                command_executor='https://{}:{}@ondemand.eu-central-1.saucelabs.com:443/wd/hub'.format(username,
                                                                                                       access_key),
                desired_capabilities=desired_cap)

    @classmethod
    def teardown_class(cls):
        """ teardown any state that was previously setup with a call to
        setup_class.
        """
        if not cls._use_remote_server:
            cls._stop_local_server()
            cls.driver.close()
        else:
            cls.driver.quit()
            cls.driver.close()

    @classmethod
    def _launch_local_server(cls):
        """ Initialise a local instance of the application.
        """
        print("Launching server...")
        cls.server_process = subprocess.Popen("python ../app.py", stdin=subprocess.PIPE, stdout=None, stderr=None,
                                              close_fds=True, shell=True)
        print("Server running...")

    @classmethod
    def _stop_local_server(cls):
        """ Shut down the application instance.
        """
        print("Stopping server...")
        cls.server_process.send_signal(signal.SIGINT)

    def _load_buzz_page(self):
        if self._use_remote_server:
            self.driver.get("https://serene-refuge-85633.herokuapp.com")
        else:
            self.driver.get("http://localhost:5000")

    def test_page_loads_without_error(self):
        self._load_buzz_page()
        assert "buzz" in self.driver.title

    def test_page_text_changes_on_reload(self):
        self._load_buzz_page()
        first_text = self.driver.find_element_by_tag_name("body").text
        #print(first_text)
        self._load_buzz_page()
        second_text = self.driver.find_element_by_tag_name("body").text
        #print(second_text)
        assert first_text is not second_text

