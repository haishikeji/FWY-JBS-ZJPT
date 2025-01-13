export const tableOption = {
  align: "center",
  index: true,
  border: true,
  gutter: 0,
  stripe: true,
  menuBtn: true,
  addBtn: false,
  emptyBtn: true,
  emptyText: "清空",
  menuAlign: "center",
  submitBtn: true,
  indexLabel: "序号",
  labelWidth: 120,
  submitText: "提交",
  labelSuffix: "：",
  menuPosition: "center",
  labelPosition: "left",
  searchMenuSpan: 6,
  dialogDirection: "rtl",
  "column": [
    {
      "type": "select",
      "label": "首次修改密码",
      "prop": "firstModify",
      "span": 12,
      "value": "1",
      "dicData": [
        {
          "label": "是",
          "value": "1"
        },
        {
          "label": "否",
          "value": "2"
        }
      ],
      "cascaderItem": [],
      "display": true,
      "props": {
        "label": "label",
        "value": "value"
      },
      "required": true,
      "rules": [
        {
          "required": true,
          "message": "请选择首次修改密码"
        }
      ],
      "placeholder": "请选择"
    },
    {
      "type": "select",
      "label": "定时修改密码",
      "prop": "timingModify",
      "value": "1",
      "dicData": [
        {
          "label": "是",
          "value": "1"
        },
        {
          "label": "否",
          "value": "2"
        }
      ],
      "cascaderItem": [],
      "display": true,
      "props": {
        "label": "label",
        "value": "value"
      },
      "placeholder": "请选择"
    },
    {
      "type": "number",
      "label": "定时修改时间",
      "prop": "timingNum",
      "placeholder": "天数",
      "minRows": 0,
      "maxRows": 999,
      "step": 1,
      "precision": 0,
      "controls": true,
      "display": true,
      "required": false,
      "rules": []
    },
    {
      "type": "select",
      "label": "验证码策略",
      "prop": "verificationCodePolicy",
      "dicData": [
        {
          "label": "不启用",
          "value": "1"
        },
        {
          "label": "必填",
          "value": "2"
        },
        {
          "label": "密码错误后必填",
          "value": "3"
        }
      ],
      "cascaderItem": [],
      "display": true,
      "props": {
        "label": "label",
        "value": "value"
      },
      "required": true,
      "rules": [
        {
          "required": true,
          "message": "请选择验证码策略"
        }
      ],
      "placeholder": "请选择",
      "value": "1",
      "span": 12
    },
    {
      "type": "number",
      "label": "密码错误次数",
      "prop": "passErrorNumCode",
      "span": 12,
      "minRows": 0,
      "maxRows": 999,
      "step": 1,
      "precision": 0,
      "controls": true,
      "display": true,
      "placeholder": "次数"
    },
    {
      "type": "number",
      "label": "密码错误次数限定时间",
      "prop": "passErrorNumTime",
      "minRows": 0,
      "maxRows": 99999,
      "step": 1,
      "precision": 0,
      "controls": true,
      "display": true,
      "placeholder": "秒"
    },

    {
      "type": "number",
      "label": "验证码位数",
      "prop": "verificationCodeLen",
      "minRows": 0,
      "maxRows": 999,
      "step": 1,
      "precision": 0,
      "controls": true,
      "display": true,
      "placeholder": "位数"
    },
    {
      "type": "input",
      "label": "验证码字典",
      "prop": "verificationCodeDice",
      "maxlength": 100,
      "span": 24,
      "placeholder": "请输入验证码字典，验证码将在字典字符中生成"
    },
    {
      "type": "number",
      "label": "密码错误次数限定时间",
      "prop": "passErrorNumTime",
      "minRows": 0,
      "maxRows": 99999,
      "step": 1,
      "precision": 0,
      "controls": true,
      "display": true,
      "placeholder": "秒"
    },
    {
      "type": "number",
      "label": "错误锁定",
      "prop": "lockingErrorNum",
      "span": 12,
      "placeholder": "0次不锁定",
      "minRows": 0,
      "maxRows": 999,
      "step": 1,
      "precision": 0,
      "controls": true,
      "display": true,
      "required": true,
      "rules": [ 
        {
          "required": true,
          "message": "错误锁定必须填写"
        }
      ]
    },
    {
      "type": "number",
      "label": "密码错误次数限定时间",
      "prop": "lockingErrorNumTime",
      "minRows": 0,
      "maxRows": 99999,
      "step": 1,
      "precision": 0,
      "controls": true,
      "display": true,
      "placeholder": "秒"
    },
    {
      "type": "number",
      "label": "锁定时间",
      "prop": "lockingErrorTime",
      "placeholder": "分钟",
      "minRows": 0,
      "maxRows": 999,
      "step": 1,
      "precision": 0,
      "controls": true,
      "display": true
    }
    ,
    {
      "type": "textarea",
      "label": "IP白名单",
      "prop": "ipWhiteList",
      "placeholder": "多个IP使用逗号隔开",
      "span":24,
      "controls": true,
      "display": true
    }
  ]
}