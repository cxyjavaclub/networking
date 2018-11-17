var serverUrl, projectName;
projectName = location.pathname;
projectName = projectName.substring(0,projectName.substr(1).indexOf('/')+1);
serverUrl = location.host + projectName;