function dateFormat(fmt, date) {
  let ret;
  const opt = {
      "Y+": date.getFullYear().toString(),        // 年
      "m+": (date.getMonth() + 1).toString(),     // 月
      "d+": date.getDate().toString(),            // 日
      "H+": date.getHours().toString(),           // 时
      "M+": date.getMinutes().toString(),         // 分
      "S+": date.getSeconds().toString()          // 秒
      // 有其他格式化字符需求可以继续添加，必须转化成字符串
  };
  for (let k in opt) {
      ret = new RegExp("(" + k + ")").exec(fmt);
      if (ret) {
          fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
      };
  };
  return fmt;
}

export const tableOption = {
  align: "center",
  index: true,
  border: true,
  // dialogType: 'drawer',
  column: [
    {
      prop: "templateId",
      label: "模板ID",
      display: false
    },
    {
      prop: "userVersion",
      label: "版本号",
      display: false
    },
    {
      prop: "userDesc",
      label: "描述",
      display: false
    },
    {
      prop: "createTime",
      label: "提交时间",
      display: false,
      formatter: function(row){
        return dateFormat("YYYY-mm-dd HH:MM",new Date(row.createTime*1000));
      }
    }
  ],
  gutter: 0,
  stripe: true,
  menuBtn: true,
  addBtn:false,
  editBtn:false,
  delBtn:false,
  emptyBtn: true,
  emptyText: "清空",
  menuAlign: "center",
  indexLabel: "序号",
  labelWidth: 120,
  labelSuffix: "：",
  menuPosition: "center",
  labelPosition: "left",
  searchMenuSpan: 6
};
