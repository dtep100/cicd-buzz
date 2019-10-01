import pytest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import os
from multiprocessing import Process
import time



# Local tests needing a web browser that should not run on the pipeline
class TestBrowserBehaviour:

    @classmethod
    def setup_class(cls):
        """ setup any state specific to the execution of the given class (which
        usually contains tests).
        """
        cls.process = Process(target=cls._launch_server)
        cls.process.start()
        time.sleep(1)
        cls.driver = webdriver.Safari()

    @classmethod
    def teardown_class(cls):
        """ teardown any state that was previously setup with a call to
        setup_class.
        """
        cls.process.terminate()
        cls.driver.close()

    @classmethod
    def _launch_server(cls):
        currPath = os.path.dirname(os.path.realpath(__file__))
        currPathElements = currPath.split("/")
        appPath = "/".join(currPathElements[0:-1])
        startCmd = "python " + appPath + "/app.py"
        print("Launching server...")
        os.system(startCmd)


    def _loadBuzzPage(self):
        self.driver.get("http://localhost:5000")

    def test_page_loads_without_error(self):
        self._loadBuzzPage()
        assert "buzz" in self.driver.title

    def test_page_text_changes_on_reload(self):
        self._loadBuzzPage()
        firstText = self.driver.find_element_by_tag_name("body").text
        #print(firstText)
        self._loadBuzzPage()
        secondText = self.driver.find_element_by_tag_name("body").text
        #print(secondText)
        assert firstText is not secondText

