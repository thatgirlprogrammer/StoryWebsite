package com.example.postgresql.controller;

class StoryNotFoundException extends RuntimeException {

  StoryNotFoundException(Long id) {
    super("Could not find story " + id);
  }
}
