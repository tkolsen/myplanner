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
    var enrollments = $http.get("../rest/enrollments").success(function(response){
        return response;
    });
    $q.all([courseList, username, modules, userHasModule, enrollments]).then(function (arrayOfResult) {
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
        compareDates();

        $scope.enrollments = arrayOfResult[4].data;
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
        console.log('clicked');
    };

    $scope.checkDeadlines = function(onlyOldestDates){
        var details = {
            "date": new Date(),
            "deadlines": $scope.userHasModule
        }

        if(onlyOldestDates){
            $http({
                method: 'POST',
                url: '../rest/checkOldestDeadlines',
                data: details
            });

        }else{
            $http({
                method: 'POST',
                url: '../rest/checkAllDeadlines',
                data: details
            }).success(function(data, status, headers, config){
                console.log("Success:\n" + data + "\n" + status + "\n" + headers + "\n" + config);
            });
        }
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
            console.log(scheduleDetails.workHoursDaily + " " + scheduleDetails.startDate);
            var details = {
                "modules": courseObj.modules,
                "workHoursDaily": scheduleDetails.workHoursDaily,
                "startDate": scheduleDetails.startDate
            };
            $http({
                method: 'POST',
                url: '../rest/generateSchedule',
                data: details
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

    $scope.hideOptions = function(){
        $scope.show = true;
        $scope.showTeacherOptions = true;
    };

    $scope.show = true;
    $scope.showTeacherOptions = true;
    $scope.showGenerator = function(){
        $scope.show = !$scope.show;
        for(i = 0; i < $scope.enrollments.length; i++){
            if($scope.enrollments[i].course_id === $scope.selectedCourse.id){
                if($scope.enrollments[i].type === 'TeacherEnrollment'){
                    console.log($scope.enrollments[i].type);
                    $scope.showTeacherOptions = false;
                    i = $scope.enrollments.length;
                }else{
                    $scope.showTeacherOptions = true;
                }
            }
        }
    };

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

    $scope.testDateCalc = function(moduleEndDate, module) {
        var todaysDate = new Date();

        var delta = Math.abs(todaysDate - moduleEndDate) / 1000;
        var days = Math.floor(delta / 86400);
        delta -= days * 86400;
        var hours = Math.floor(delta / 3600) % 24;

        if(module.completed_at != null){
            return 'Modul ferdig'
        }else if(todaysDate < moduleEndDate){
            return 'Tid til frist: ' + days + ' dager, ' + hours + ' timer.'
        }else{
            return 'Fristen gikk ut for ' + days + ' dager og ' + hours + ' timer siden.'
        }

    };
});