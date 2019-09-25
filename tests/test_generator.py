import pytest

from buzz import generator


def test_sample_single_word():
    word_list = ('foo', 'bar', 'foobar')
    word = generator.sample(word_list)
    assert word in word_list


def test_sample_multiple_words():
    word_list = ('foo', 'bar', 'foobar')
    words = generator.sample(word_list, 2)
    assert len(words) == 2
    assert words[0] in word_list
    assert words[1] in word_list
    assert words[0] is words[1]


def test_generate_buzz_of_at_least_five_words():
    phrase = generator.generate_buzz()
    assert len(phrase.split()) >= 5

