import pytest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import subprocess
import signal
import urllib3
import os
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)


class TestBrowserBehaviour:

    @classmethod
    def setup_class(cls):
        """ setup any state specific to the execution of the given class (which
        usually contains tests).
        """
        print (os.environ["CI"])
        cls._use_remote_driver = False
        if os.environ["CI"] == "true":
            cls._use_remote_driver = True

        if not cls._use_remote_driver:
            cls._launch_local_server()
            cls.driver = webdriver.Safari()
        else:
            # The command_executor tells the test to run on Sauce, while the desired_capabilities
            # parameter tells us which browsers and OS to spin up.
            desired_cap = {
                'platform': "Mac OS X 10.13",
                'browserName': "safari",
                'version': "11.1",
                'build': os.environ["TRAVIS_BUILD_NUMBER"],
                'name': "TestBrowserBehaviour tests",
                'tags': [os.environ["TRAVIS_PYTHON_VERSION"], "CI"],
                'tunnel-identifier': os.environ["TRAVIS_JOB_NUMBER"],
            }

            username = os.environ["SAUCE_USERNAME"]
            access_key = os.environ["SAUCE_ACCESS_KEY"]

            hub_url = "%s:%s@localhost:4445" % (username, access_key)
            cmd_exec = "http://%s/wd/hub" % hub_url

            cls.driver = webdriver.Remote(command_executor=cmd_exec, desired_capabilities=desired_cap)

    @classmethod
    def teardown_class(cls):
        """ teardown any state that was previously setup with a call to
        setup_class.
        """

        if not cls._use_remote_driver:
            cls._stop_local_server()
            cls.driver.close()
        else:
            cls.driver.quit()

    @classmethod
    def _launch_local_server(cls):
        """ Initialise a local instance of the application.
        """
        print("Launching local server...")
        cls.server_process = subprocess.Popen("python ../app.py", stdin=subprocess.PIPE, stdout=None, stderr=None,
                                              close_fds=True, shell=True)
        print("Server running...")

    @classmethod
    def _stop_local_server(cls):
        """ Shut down the application instance.
        """
        print("Stopping local server...")
        cls.server_process.send_signal(signal.SIGINT)

    def _load_buzz_page(self):
        self.driver.get("http://localhost:5000")

    def test_page_loads_without_error(self):
        self._load_buzz_page()
        assert "buzz" in self.driver.title

    def test_page_text_changes_on_reload(self):
        self._load_buzz_page()
        first_text = self.driver.find_element_by_tag_name("body").text
        print("\"" + first_text + "\"")
        self._load_buzz_page()
        second_text = self.driver.find_element_by_tag_name("body").text
        print("\"" + second_text + "\"")
        assert first_text != second_text

