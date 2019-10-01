import pytest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import subprocess
import signal


# Local tests needing a web browser that should not run on the pipeline
class TestBrowserBehaviour:

    @classmethod
    def setup_class(cls):
        """ setup any state specific to the execution of the given class (which
        usually contains tests).
        """
        cls._launch_server()
        cls.driver = webdriver.Safari()

    @classmethod
    def teardown_class(cls):
        """ teardown any state that was previously setup with a call to
        setup_class.
        """
        cls._stop_server()
        cls.driver.close()

    @classmethod
    def _launch_server(cls):
        """ Initialise a local instance of the application.
        """
        print("Launching server...")
        cls.server_process = subprocess.Popen("python ../app.py", stdin=subprocess.PIPE, stdout=None, stderr=None,
                                              close_fds=True, shell=True)
        print("Server running...")

    @classmethod
    def _stop_server(cls):
        """ Shut down the application instance.
        """
        print("Stopping server...")
        cls.server_process.send_signal(signal.SIGINT)

    def _load_buzz_page(self):
        self.driver.get("http://localhost:5000")

    def test_page_loads_without_error(self):
        self._load_buzz_page()
        assert "buzz" in self.driver.title

    def test_page_text_changes_on_reload(self):
        self._load_buzz_page()
        firstText = self.driver.find_element_by_tag_name("body").text
        #print(firstText)
        self._load_buzz_page()
        secondText = self.driver.find_element_by_tag_name("body").text
        #print(secondText)
        assert firstText is not secondText

