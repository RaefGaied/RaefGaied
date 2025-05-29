const thisYear = new Date().getFullYear()
const startTimeOfThisYear = new Date(`${thisYear}-01-01T00:00:00+00:00`).getTime()
const endTimeOfThisYear = new Date(`${thisYear}-12-31T23:59:59+00:00`).getTime()
const progressOfThisYear = (Date.now() - startTimeOfThisYear) / (endTimeOfThisYear - startTimeOfThisYear)
const progressBarOfThisYear = generateProgressBar()

let monthNames = ["Jan","Feb","Mar","Apr", "May","Jun","Jul","Aug", "Sep", "Oct","Nov","Dec"];

function generateProgressBar() {
    const progressBarCapacity = 30
    const passedProgressBarIndex = parseInt(progressOfThisYear * progressBarCapacity)
    const progressBar = Array(progressBarCapacity)
        .fill('▁')
        .map((value, index) => index < passedProgressBarIndex ? '█' : value)
        .join('')
    return `{ ${progressBar} }`
}

const readme = `\
# Hi there! <img src="https://github.com/TheDudeThatCode/TheDudeThatCode/blob/master/Assets/Hi.gif" width="35" />
<p align="center">
<a href="https://twitter.com/raefgaied" target="blank"><img align="center" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/twitter.svg" alt="raefgaied" height="30" width="30" /></a>&nbsp;
<a href="https://linkedin.com/in/raef-gaied" target="blank"><img align="center" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/linkedin.svg" alt="raef-gaied" height="30" width="30" /></a>&nbsp;
<a href="http://discord.com/users/raefgaied" target="blank"><img align="center" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/discord.svg" alt="raefgaied" height="40" width="30" /></a>&nbsp;
<a href="https://www.buymeacoffee.com/raefgaied"><img align="center" alt="Buy me a Coffee" width="30px" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/buymeacoffee.svg" /></a>
</p>

![](https://camo.githubusercontent.com/992babdffd8c74a1502de375fbdf7e4d54773242/68747470733a2f2f6d656469612e67697068792e636f6d2f6d656469612f53576f536b4e36447854737a71494b4571762f67697068792e676966)

### <img src="https://github.com/TheDudeThatCode/TheDudeThatCode/blob/master/Assets/Developer.gif" width="45" /> About Me:
- 🏦 I'm a Software Developer passionate about technology and innovation
      <img src="https://media.giphy.com/media/WUlplcMpOCEmTGBtBW/giphy.gif" width="30">
- 📝 I regularly write technical articles on [Medium](https://medium.com/@raef.gaied)
- 💻 I use daily: **.js**, **.py**, **.html**, **.css**
- 📖 I am currently learning new technologies and best practices
- 💬 Talk to me about web development, programming, and technology
- 👯 We can connect to collaborate on interesting projects
- ⚡ Fun fact: I love creating beautiful and functional applications
- 🧑‍💻 Tech I work on :

<p align="center">
      <img src="https://www.vectorlogo.zone/logos/javascript/javascript-icon.svg" alt="javascript" width="55" height="55"/>
      <img src="https://www.vectorlogo.zone/logos/python/python-icon.svg" alt="python" width="55" height="55"/>
      <img src="https://www.vectorlogo.zone/logos/w3_html5/w3_html5-icon.svg" alt="html5" width="55" height="55"/>
      <img src="https://www.vectorlogo.zone/logos/w3_css/w3_css-icon.svg" alt="css3" width="55" height="55"/>
      <img src="https://www.vectorlogo.zone/logos/nodejs/nodejs-icon.svg" alt="nodejs" width="55" height="55"/>
      <img src="https://www.vectorlogo.zone/logos/reactjs/reactjs-icon.svg" alt="react" width="55" height="55"/>
      <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="55" height="55"/> 
      <img src="https://www.vectorlogo.zone/logos/github/github-icon.svg" alt="github" width="55" height="55"/>
      <img src="https://www.vectorlogo.zone/logos/visualstudio_code/visualstudio_code-icon.svg" alt="vscode" width="55" height="55"/>
</p>

---
### <img src='https://media1.giphy.com/media/du3J3cXyzhj75IOgvA/giphy.gif?cid=ecf05e47x2g034i9pzwtzzsd3xgg2w9nr94t4tflbbgo3008&rid=giphy.gif' width='25' /> My Github Stats:
![Raef's github stats](https://github-readme-stats.vercel.app/api?username=RaefGaied&show_icons=true&title_color=ffc857&icon_color=8ac926&text_color=daf7dc&bg_color=151515&hide=issues&count_private=true&include_all_commits=true)
[![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=RaefGaied&layout=compact&text_color=daf7dc&bg_color=151515&hide=css,html,php)](https://github.com/anuraghazra/github-readme-stats)
[![GitHub Streak](https://github-readme-streak-stats.herokuapp.com/?user=RaefGaied&theme=dark)](https://git.io/streak-stats)

<!--START_SECTION:waka-->

<!--END_SECTION:waka-->

⏳ **Year Progress** ${progressBarOfThisYear} ${(progressOfThisYear * 100).toFixed(2)} % as on ⏰ ${(new Date().getDate()-1)+'-'+ monthNames[new Date().getMonth()]+'-'+new Date().getFullYear()}

---

### <img src = "https://media1.giphy.com/media/JZ40cnfnN11KycrvMF/giphy.gif?cid=ecf05e47a0n3gi1bfqntqmob8g9aid1oyj2wr3ds3mg700bl&rid=giphy.gif" width = '23' /> My Latest Blog posts:
<!-- BLOG-POST-LIST:START -->
<!-- BLOG-POST-LIST:END -->

▶ [... view more](https://medium.com/@raef.gaied)

---

### <img alt="GIF" src="https://github.com/TheDudeThatCode/TheDudeThatCode/blob/master/Assets/hmm.gif" width="20" /> A Famous Fact/Quote:
<a href="https://github.com/marketplace/actions/quote-readme">
<!--STARTS_HERE_QUOTE_README-->
<i>❝The best way to predict the future is to create it.❞</i>
<!--ENDS_HERE_QUOTE_README-->
</a>

---

### <img align ='center' src='https://media2.giphy.com/media/UQDSBzfyiBKvgFcSTw/giphy.gif?cid=ecf05e47p3cd513axbek3f56ti3jzizq8hincw20jauyyfyw&rid=giphy.gif' width ='29' /> Here's some humor for you:
<img src="https://readme-jokes.vercel.app/api" alt="Error fetching resource, Refresh again to view Jokes Card" width = '11000' />
`
console.log(readme)
