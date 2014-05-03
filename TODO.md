# This is developer preview integration with HttpClient 4.3.3 (android port from HC Apache team)
====

Todo:

  - Complete interfacing all components
  - Allow developers to provide their own implementation of almost any part of library (RequestParams, HttpClient configuration, Response Handler, ...)
  - Get rid of unchecked warnings in RequestParams (generify or get rid of unchecked collections)
  - Complete configuration integration with HC4
  - Clear out deprecated methods
  - Clear out ambiguous variants of methods in Response Handlers
  - Add use-case test within Sample application for every function (better than non-ui tests as it allows developers to check how device behaves too)
  - Get rid of non-standard implementation of entities, streams handling
  - Allow single entry-point configuration for both asynchronous and synchronous usages
  - Allow per-request async/sync switch (???)
  - Use standardized retry handler
  - Don't stuck with old-fashioned method calls (don't need to keep compatibility to oldest versions)
  - Use fluent interfaces way (https://en.wikipedia.org/wiki/Fluent_interface#Java)
  - Add full HTTP protocol implementation (eg. for FULL REST API)
  - Add encoding/decoding integration (json, hessian, proto-buffers, ...)

