urn
===

[![Build Status](https://travis-ci.org/sangupta/urn.svg?branch=master)](https://travis-ci.org/sangupta/urn)
[![Coverage Status](https://coveralls.io/repos/github/sangupta/urn/badge.svg?branch=master)](https://coveralls.io/github/sangupta/urn?branch=master)
[![Maven Version](https://maven-badges.herokuapp.com/maven-central/com.sangupta/urn/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.sangupta/urn)

**urn** is a Java library that helps integrate with different object stores via a simple API, allowing you to change
the provider at runtime without changing the code.

Currently available object store providers:

* In-memory
* Flat-file on disk
* Redis
* MongoDB

Ones in the pipeline:

* Amazon S3
* MongoDB GridFS
* SeaWeedFS
* LevelDB
* RocksDB
* SQlite

Features
--------

- Ability for time-based eviction of each individual asset (depends on provider)
- Metadata storage with object like MIME type
- Custom name that can be set, different from the unique key

Feature Roadmap
---------------

- A simple server that can serve the raw stream using the key


Versioning
----------

For transparency and insight into our release cycle, and for striving to maintain backward compatibility, 
`urn` will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

License
-------
	
```
urn - Object storage library
Copyright (c) 2016, Sandeep Gupta

http://sangupta.com/projects/urn

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
