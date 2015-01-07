app.controller("CoursesCtrl", function ($scope, $http, $q) {
    $scope.quantity = 5;
    $scope.text = 'Vis flere..';

    var courseList = $http.get("../rest/courses").success(function (response) {
        return response;
    });
    var username = $http.get("../rest/userName").success(function (response) {
        return response;
    });
    var modules = $http.get("../rest/modules").success(function(response) {
        return response;
    });
    var userHasModule = $http.get("../rest/userHasModule").success(function(response){
        return response;
    });
    $q.all([courseList, username, modules, userHasModule]).then(function (arrayOfResult) {
        $scope.courses = arrayOfResult[0].data;
        $scope.selectedCourse = $scope.courses[0];
        $scope.username = arrayOfResult[1].data.user.name;
        $scope.user = arrayOfResult[1].data.user;
        $scope.modules = arrayOfResult[2].data;
        $scope.userHasModule = arrayOfResult[3].data;
        $scope.courses.forEach(function(c){
            c.modules = new Array();
            $scope.modules.forEach(function(m){
                if(m.course.id == c.id){
                    c.modules.push(m);
                }
            });
        });
        console.log($scope.userHasModule);
        compareDates();
    });
    var compareDates = function(){
        $scope.modules.forEach(function(m){
            $scope.userHasModule.forEach(function(u){
                if(u.userHasModulePK.module.id === m.id){
                    m.newStartDate = formatDate(u.startDate);
                    m.newEndDate = formatDate(u.endDate);
                }
            });
        });
    };

    var formatDate = function(dateString){
        var date = new Date(dateString);
        return date;
    };

    $scope.moduleClicked = function(){
        console.log("module clicked");
    };

    $scope.checkAllDeadlines = function(deadlineDetails){

    };

    $scope.checkOldestDeadlines = function(deadlineDetails){
        // TODO: implement me
    };

    $scope.generateSchedule = function(scheduleDetails){
        if(confirm("Dette vil overskrive din nåværende fremdriftsplan om den allerede eksisterer og generere en ny fremdriftsplan, og kan ikke tilbakestilles. Vil du fortsette?")){

            var courseName = $scope.selectedCourse.name;
            var courseObj;
            $scope.courses.forEach(function(course){
                if(course.name === courseName){
                    courseObj = course;
                }
            });

            var details = {
                "modules": courseObj.modules,
                "workHoursDaily": scheduleDetails.workHoursDaily,
                "startDate": scheduleDetails.startDate
            };
            console.log(details);
            $http({
                method: 'POST',
                url: '../rest/generateSchedule',
                data: details
            }).success(function(){
                console.log('heilt ok');
            });
        }
    };

    $scope.test = function(course, index){
        var module = course.modules[index];
        var items = module.items;
        var completionReq = new Array();
        items.forEach(function(item){
            if(item.completion_requirement != null){
                completionReq.push(item.completion_requirement);
            }
        });
        var count = completionReq.length;
        var countCompleted = 0;
        completionReq.forEach(function(req){
            if(req.completed){
                countCompleted++;
            }
        });

        if(count != 0){
            module.width = countCompleted / count * 100 + '%';
        }else if(count == 0 || count == null){
            module.width = 100+'%';
        }
    }

    $scope.submit = function(module){
        var moduleId = module.id;
        var newStartDate = module.newStartDate;
        var newEndDate = module.newEndDate;
        var userHasModule = {
            "startDate": newStartDate,
            "endDate": newEndDate,
            "module": module,
            "user":{
                "id": $scope.user.id
            }
        };
        $http({
            method: 'PUT',
            url: '../rest/updateDates',
            data: userHasModule
        }).success(function(response){
            alert('Dato lagret');
        });
    };
});