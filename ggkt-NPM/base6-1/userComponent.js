"use strict";

var _userApi = require("./userApi.js");

// 这时程序无法运行，因为ES6的模块化无法在Node.js中执行，需要用Babel编辑成ES5后再执行。
(0, _userApi.getList)(); //只取需要的方法即可，多个方法用逗号分隔

(0, _userApi.save)();