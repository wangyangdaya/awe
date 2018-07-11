"use strict"
var dom = document.getElementById("container");
var myChart = echarts.init(dom);

var UPDATE_DURATION = 100;

window.AudioContext = window.AudioContext || window.webkitAudioContext;

var audioContext = new AudioContext();

var oReq = new XMLHttpRequest();
oReq.open('GET', '/awt/mp3/Here i am－Bryan Adams.mp3', true);
oReq.responseType = 'arraybuffer';

oReq.onload = function (e) {
    audioContext.decodeAudioData(oReq.response, initVisualizer);
};
oReq.send();

function initVisualizer(audioBuffer) {
    let inited = true;

    var source = audioContext.createBufferSource();
    source.buffer = audioBuffer;

    // Must invoked right after click event
    if (source.noteOn) {
        source.noteOn(0);
    }
    else {
        source.start(0);
    }

    var analyzer = audioContext.createAnalyser();
    var gainNode = audioContext.createGain();
    analyzer.fftSize = 4096;

    gainNode.gain.value = 1;
    source.connect(gainNode);
    gainNode.connect(analyzer);
    analyzer.connect(audioContext.destination);

    var frequencyBinCount = analyzer.frequencyBinCount;
    var dataArray = new Uint8Array(frequencyBinCount);


    var beta = 0;

    function update() {
        analyzer.getByteFrequencyData(dataArray);

        var item = [];
        var size = 50;
        var dataProvider = [];

        for (var i = 0; i < size * size; i++) {
            var x = i % size;
            var y = Math.floor(i / size);
            var dx = x - size / 2;
            var dy = y - size / 2;

            var angle = Math.atan2(dy, dx);
            if (angle < 0) {
                angle = Math.PI * 2 + angle;
            }
            var dist = Math.sqrt(dx * dx + dy * dy);
            var idx = Math.min(
                frequencyBinCount - 1, Math.round(angle / Math.PI / 2 * 60 + dist * 60) + 100
            );

            var val = Math.pow(dataArray[idx] / 100, 3);
            dataProvider.push([x, y, Math.max(val, 0.1)]);
        }

        myChart.setOption({
            grid3D: {
                viewControl: {
                    beta: beta,
                    alpha: Math.sin(beta / 10 + 40) * (beta % 10 + 5) / 15 * 30 + 30,
                    distance: Math.cos(beta / 50 + 20) * (beta % 10 + 5) / 15 * 50 + 80,
                    animationDurationUpdate: UPDATE_DURATION,
                    animationEasingUpdate: 'linear'
                }
            },
            series: [{
                data: dataProvider
            }]
        });
        beta += 2;

        setTimeout(update, UPDATE_DURATION);
    };

    update();
}

let option = {
    tooltip: {},
    visualMap: {
        show: false,
        min: 0.1,
        max: 4,
        inRange: {
            color: ['#010103', '#2f490c', '#b0b70f', '#fdff44', '#fff']
        }
    },
    xAxis3D: {
        type: 'value'
    },
    yAxis3D: {
        type: 'value'
    },
    zAxis3D: {
        type: 'value',
        min: -6,
        max: 6
    },
    grid3D: {
        show: false,
        environment: '#000',
        viewControl: {
            distance: 100
        },
        postEffect: {
            enable: true,
            FXAA: {
                enable: true
            }
        },
        light: {
            main: {
                shadow: true,
                intensity: 10,
                quality: 'high'
            },
            ambientCubemap: {
                // texture: 'data-gl/asset/canyon.hdr',
                exposure: 0,
                diffuseIntensity: 0.2
            }
        }
    },
    series: [{
        type: 'bar3D',
        silent: true,
        shading: 'lambert',
        data: [],
        barSize: 1,
        lineStyle: {
            width: 4
        },
        // animation: false,
        animationDurationUpdate: UPDATE_DURATION
    }]
}

if (option && typeof option === "object") {
    myChart.setOption(option, true);
}

const login = () => {
    let validate = $('#login_form').data('bootstrapValidator')
    validate.validate();
    if (validate.isValid()) {
        let params = {"username": $('#username').val(), "password": $('#password').val()}
        let url = "userService/login"
        AjaxUtils.post(url, params, function (response) {
            console.log(response)
            window.location.href = 'index'
        })
    }

}

$('#login_form').bootstrapValidator({
    message: '输入值不合法',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        username: {
            message: '用户名不合法',
            validators: {
                notEmpty: {
                    message: '用户名不能为空'
                },
                stringLength: {
                    min: 3,
                    max: 30,
                    message: '请输入3到30个字符'
                },
                regexp: {
                    regexp: /^[a-zA-Z0-9_\. \u4e00-\u9fa5 ]+$/,
                    message: '用户名只能由字母、数字、点、下划线和汉字组成 '
                }
            }, password:{
                notEmpty: {
                    message: '密码不能为空'
                }
            }, email: {
                validators: {
                    notEmpty: {
                        message: 'email不能为空'
                    },
                    emailAddress: {
                        message: '请输入正确的邮件地址如：123@qq.com'
                    }
                }
            }, phone: {
                validators: {
                    notEmpty: {
                        message: '手机号不能为空'
                    },
                    regexp: {
                        regexp: "^([0-9]{11})?$",
                        message: '手机号码格式错误'
                    }
                }
            }, address: {
                validators: {
                    notEmpty: {
                        message: '地址不能为空'
                    }, stringLength: {
                        min: 8,
                        max: 60,
                        message: '请输入5到60个字符'
                    }
                }
            }
        }
    }
})

