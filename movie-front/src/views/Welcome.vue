<template>
  <div class="welcome-container">
    <!-- 按钮组 -->
    <div class="button-group">
      <el-button-group>
        <el-button 
          type="primary" 
          :plain="!showBoth" 
          @click="showBothCharts"
          icon="el-icon-data-analysis">
          显示所有图表
        </el-button>
      </el-button-group>
    </div>

    <!-- 图表区域 - 修改为并排显示 -->
    <div class="charts-row">
      <div class="chart-container">
        <div id="pieChart"></div>
      </div>
      <div class="chart-container">
        <div id="barChart"></div>
      </div>
    </div>
  </div>
</template>


<script>
import * as echarts from 'echarts'
import request from '@/utils/request'

export default {
  name: "Welcome",
  data() {
  return {
    showBoth: true,
    pieChart: null,
    barChart: null,
    boxOfficeData: []  // 添加票房数据存储
  }
},
  mounted() {
    // 移除 setTimeout，改用 nextTick
    this.$nextTick(() => {
      const chartDom = document.getElementById('pieChart')
      if (chartDom) {
        this.fetchCategoryData()
      }
      this.initBarChart()
      this.fetchBoxOfficeData()  // 添加获取票房数据
      window.addEventListener('resize', this.resizeCharts)
    })
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeCharts)
    if (this.pieChart) {
      this.pieChart.dispose()
    }
    if (this.barChart) {
      this.barChart.dispose()
    }
  },
  methods: {
    async fetchCategoryData() {
      try {
        const res = await request.get('http://localhost:8181/sysMovieCategory/statistics')
        if (res.code === 200 && res.data) {
          // 确保数据存在且格式正确
          console.log('获取到的数据:', res.data) // 添加日志
          this.initPieChart(res.data)
        } else {
          this.$message.error('获取电影类型统计失败')
        }
      } catch (error) {
        console.error('获取电影类型统计出错:', error)
        this.$message.error('获取电影类型统计失败')
      }
    },
    showBothCharts() {
      this.showBoth = true
      this.$nextTick(() => {
        this.resizeCharts()
      })
    },
// 初始化电影类型分布图
initPieChart(data) {
  const chartDom = document.getElementById('pieChart')
  if (!chartDom) return
  
  if (this.pieChart) {
    this.pieChart.dispose()
  }
  
  this.pieChart = echarts.init(chartDom)
  
  const pieOption = {
    title: {
      text: '电影类型分布',
      left: 'center',
      top: 10,
      textStyle: {
        fontSize: 20,
        fontWeight: 'bold'
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c}部 ({d}%)' // 显示名称、数量和百分比
    },
    legend: {
      type: 'scroll',
      orient: 'vertical',
      right: 10,
      top: 50,
      bottom: 20,
      data: data.map(item => item.name), // 使用数据中的名称作为图例
      textStyle: {
        fontSize: 12,
        fontWeight: 'bold',
        fontFamily: 'sans-serif',
      }
    },
    series: [
      {
        name: '电影类型',
        type: 'pie',
        radius: '70%',
        center: ['40%', '50%'], // 调整中心位置
        data: data.map(item => ({
          name: item.name,
          value: item.value
        })),
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, .5)'
          }
        },
        label: {
          show: true,
          position: 'outside', // 标签位置设置为外部
          formatter: '{b}\n{d}%', // 显示名称和百分比
          fontSize: 12,
          fontWeight: 'bold',
          fontFamily: 'sans-serif',
        }
      }
    ]
  }
  
  try {
    this.pieChart.setOption(pieOption)
  } catch (error) {
    console.error('图表初始化失败:', error)
  }
},
async fetchBoxOfficeData() {
  try {
    const res = await request.get('http://localhost:8181/sysMovie/boxOffice')
    if (res.code === 200) {  // 检查统一的返回码
      this.boxOfficeData = res.data.map(item => ({
        movieName: item.movieName,  // 确保字段名匹配
        boxOffice: parseFloat(item.boxOffice || 0)
      })).filter(item => item.boxOffice > 0)
      
      this.initBarChart()
    } else {
      this.$message.error('获取票房统计失败: ' + res.msg)
    }
  } catch (error) {
    console.error('获取票房统计出错:', error)
    this.$message.error('获取票房统计失败')
  }
},
  // 修改初始化票房图表的方法
  initBarChart() {
  const chartDom = document.getElementById('barChart')
  if (!chartDom || !this.boxOfficeData.length) return
  
  if (this.barChart) {
    this.barChart.dispose()
  }
  
  this.barChart = echarts.init(chartDom)
  
  // 定义颜色方案
  const colorPalette = {
    first: ['#ffd700', '#ffa500'],    // 金色渐变
    second: ['#95de64', '#52c41a'],  // 银色渐变
    third: ['#cd7f32', '#a0522d'],    // 铜色渐变
    normal: ['#1890ff', '#36cfc9']    // 普通蓝色渐变
  }
  
  const barOption = {
    title: {
      text: '热门电影票房统计（TOP10）',
      left: 'center',
      top: '3%',
      textStyle: {
        fontSize: 22,
        fontWeight: 'bold'
      }
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      },
      formatter: function(params) {
        const data = params[0]
        return `${data.name}<br/>票房：${data.value.toLocaleString()}元`  // 使用千分位格式化
      }
    },
    grid: {
      left: '5%',
      right: '5%',
      bottom: '15%',
      top: '15%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: this.boxOfficeData.map(item => item.movieName),
      axisLabel: {
        interval: 0,
        rotate: 30,
        fontSize: 12,
        formatter: function(value) {
          return value.length > 8 ? value.substring(0, 8) + '...' : value
        }
      },
      axisTick: {
        alignWithLabel: true
      }
    },
    yAxis: {
      type: 'value',
      name: '票房(元)',  // 修改为万元
      nameTextStyle: {
        padding: [0, 0, 0, 40],
        fontSize: 14
      },
      axisLabel: {
        formatter: function(value) {
          return value.toLocaleString()  // 使用千分位格式化
        },
        fontSize: 12
      },
      splitLine: {
        lineStyle: {
          type: 'dashed'
        }
      }
    },
    series: [
      {
        data: this.boxOfficeData.map((item, index) => ({
          value: parseFloat(item.boxOffice),
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { 
                offset: 0, 
                color: index === 0 ? colorPalette.first[0] : 
                       index === 1 ? colorPalette.second[0] : 
                       index === 2 ? colorPalette.third[0] : 
                       colorPalette.normal[0] 
              },
              { 
                offset: 1, 
                color: index === 0 ? colorPalette.first[1] : 
                       index === 1 ? colorPalette.second[1] : 
                       index === 2 ? colorPalette.third[1] : 
                       colorPalette.normal[1] 
              }
            ])
          }
        })),
        type: 'bar',
        showBackground: true,
        backgroundStyle: {
          color: 'rgba(180, 180, 180, 0.1)'
        },
        barWidth: '40%',
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowColor: 'rgba(0,0,0,0.3)'
          }
        },
        label: {
          show: true,
          position: 'top',
          formatter: function(params) {
            return params.value.toFixed(0) + '元'  // 修改为万元
          },
          fontSize: 12,
          color: '#666'
        }
      }
    ]
  }
  
  try {
    this.barChart.setOption(barOption)
  } catch (error) {
    console.error('票房图表初始化失败:', error)
    this.$message.error('票房图表初始化失败')
  }
},
    // 调整图表大小
   // 调整图表大小
   resizeCharts() {
      if (this.pieChart) {
        this.pieChart.resize()
      }
      if (this.barChart) {
        this.barChart.resize()
      }
    }
  }

}
</script>
<style scoped>
.welcome-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 10px;
  margin: 20px;
  padding: 20px;
}
.button-group {
  display: flex;
  justify-content: center;
  margin-bottom: 30px;
}
.charts-row {
  flex: 1;
  display: flex;
  justify-content: space-between;
  gap: 20px;  /* 添加间距 */
}
.chart-container {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: calc(50% - 10px);  /* 修改宽度为50%减去间距的一半 */
  height: 600px;
}
#pieChart,
#barChart {
  width: 100% !important;
  height: 100% !important;
  min-width: unset;
  min-height: unset;
}

/* 按钮hover效果 */
.el-button {
  transition: all 0.3s;
}
.el-button:hover {
  transform: translateY(-2px);
}
/* 响应式布局 */
@media screen and (max-width: 1400px) {
  .charts-row {
    flex-direction: column;  /* 在小屏幕上改为垂直排列 */
  }
  
  #pieChart,
  #barChart {
    min-width: 100%;
    height: 350px;
  }
}
</style>