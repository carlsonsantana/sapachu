var parser=function(){function n(){this.yy={}}var e={trace:function(){},yy:{},symbols_:{error:2,expressions:3,polygon:4,EOF:5,"(":6,points:7,")":8,",":9,point:10,NUMBER:11,$accept:0,$end:1},terminals_:{2:"error",5:"EOF",6:"(",8:")",9:",",11:"NUMBER"},productions_:[0,[3,2],[4,3],[7,3],[7,1],[10,5]],performAction:function(t,n,r,i,s,o,u){var a=o.length-1;switch(s){case 1:return o[a-1];break;case 2:this.$=o[a-1];break;case 3:var f=o[a-2];f.push(o[a]);this.$=f;break;case 4:var f=[];f.push(o[a]);this.$=f;break;case 5:this.$={x:o[a-3],y:o[a-1]};break}},table:[{3:1,4:2,6:[1,3]},{1:[3]},{5:[1,4]},{6:[1,7],7:5,10:6},{1:[2,1]},{8:[1,8],9:[1,9]},{8:[2,4],9:[2,4]},{11:[1,10]},{5:[2,2]},{6:[1,7],10:11},{9:[1,12]},{8:[2,3],9:[2,3]},{11:[1,13]},{8:[1,14]},{8:[2,5],9:[2,5]}],defaultActions:{4:[2,1],8:[2,2]},parseError:function(t,n){if(n.recoverable){this.trace(t)}else{throw new Error(t)}},parse:function(t){function m(e){r.length=r.length-2*e;i.length=i.length-e;s.length=s.length-e}function g(){var e;e=n.lexer.lex()||h;if(typeof e!=="number"){e=n.symbols_[e]||e}return e}var n=this,r=[0],i=[null],s=[],o=this.table,u="",a=0,f=0,l=0,c=2,h=1;var p=s.slice.call(arguments,1);this.lexer.setInput(t);this.lexer.yy=this.yy;this.yy.lexer=this.lexer;this.yy.parser=this;if(typeof this.lexer.yylloc=="undefined"){this.lexer.yylloc={}}var d=this.lexer.yylloc;s.push(d);var v=this.lexer.options&&this.lexer.options.ranges;if(typeof this.yy.parseError==="function"){this.parseError=this.yy.parseError}else{this.parseError=Object.getPrototypeOf(this).parseError}var y,b,w,E,S,x,T={},N,C,k,L;while(true){w=r[r.length-1];if(this.defaultActions[w]){E=this.defaultActions[w]}else{if(y===null||typeof y=="undefined"){y=g()}E=o[w]&&o[w][y]}if(typeof E==="undefined"||!E.length||!E[0]){var A="";L=[];for(N in o[w]){if(this.terminals_[N]&&N>c){L.push("'"+this.terminals_[N]+"'")}}if(this.lexer.showPosition){A="Parse error on line "+(a+1)+":\n"+this.lexer.showPosition()+"\nExpecting "+L.join(", ")+", got '"+(this.terminals_[y]||y)+"'"}else{A="Parse error on line "+(a+1)+": Unexpected "+(y==h?"end of input":"'"+(this.terminals_[y]||y)+"'")}this.parseError(A,{text:this.lexer.match,token:this.terminals_[y]||y,line:this.lexer.yylineno,loc:d,expected:L})}if(E[0]instanceof Array&&E.length>1){throw new Error("Parse Error: multiple actions possible at state: "+w+", token: "+y)}switch(E[0]){case 1:r.push(y);i.push(this.lexer.yytext);s.push(this.lexer.yylloc);r.push(E[1]);y=null;if(!b){f=this.lexer.yyleng;u=this.lexer.yytext;a=this.lexer.yylineno;d=this.lexer.yylloc;if(l>0){l--}}else{y=b;b=null}break;case 2:C=this.productions_[E[1]][1];T.$=i[i.length-C];T._$={first_line:s[s.length-(C||1)].first_line,last_line:s[s.length-1].last_line,first_column:s[s.length-(C||1)].first_column,last_column:s[s.length-1].last_column};if(v){T._$.range=[s[s.length-(C||1)].range[0],s[s.length-1].range[1]]}x=this.performAction.apply(T,[u,f,a,this.yy,E[1],i,s].concat(p));if(typeof x!=="undefined"){return x}if(C){r=r.slice(0,-1*C*2);i=i.slice(0,-1*C);s=s.slice(0,-1*C)}r.push(this.productions_[E[1]][0]);i.push(T.$);s.push(T._$);k=o[r[r.length-2]][r[r.length-1]];r.push(k);break;case 3:return true}}return true}};var t=function(){var e={EOF:1,parseError:function(t,n){if(this.yy.parser){this.yy.parser.parseError(t,n)}else{throw new Error(t)}},setInput:function(e){this._input=e;this._more=this._backtrack=this.done=false;this.yylineno=this.yyleng=0;this.yytext=this.matched=this.match="";this.conditionStack=["INITIAL"];this.yylloc={first_line:1,first_column:0,last_line:1,last_column:0};if(this.options.ranges){this.yylloc.range=[0,0]}this.offset=0;return this},input:function(){var e=this._input[0];this.yytext+=e;this.yyleng++;this.offset++;this.match+=e;this.matched+=e;var t=e.match(/(?:\r\n?|\n).*/g);if(t){this.yylineno++;this.yylloc.last_line++}else{this.yylloc.last_column++}if(this.options.ranges){this.yylloc.range[1]++}this._input=this._input.slice(1);return e},unput:function(e){var t=e.length;var n=e.split(/(?:\r\n?|\n)/g);this._input=e+this._input;this.yytext=this.yytext.substr(0,this.yytext.length-t-1);this.offset-=t;var r=this.match.split(/(?:\r\n?|\n)/g);this.match=this.match.substr(0,this.match.length-1);this.matched=this.matched.substr(0,this.matched.length-1);if(n.length-1){this.yylineno-=n.length-1}var i=this.yylloc.range;this.yylloc={first_line:this.yylloc.first_line,last_line:this.yylineno+1,first_column:this.yylloc.first_column,last_column:n?(n.length===r.length?this.yylloc.first_column:0)+r[r.length-n.length].length-n[0].length:this.yylloc.first_column-t};if(this.options.ranges){this.yylloc.range=[i[0],i[0]+this.yyleng-t]}this.yyleng=this.yytext.length;return this},more:function(){this._more=true;return this},reject:function(){if(this.options.backtrack_lexer){this._backtrack=true}else{return this.parseError("Lexical error on line "+(this.yylineno+1)+". You can only invoke reject() in the lexer when the lexer is of the backtracking persuasion (options.backtrack_lexer = true).\n"+this.showPosition(),{text:"",token:null,line:this.yylineno})}return this},less:function(e){this.unput(this.match.slice(e))},pastInput:function(){var e=this.matched.substr(0,this.matched.length-this.match.length);return(e.length>20?"...":"")+e.substr(-20).replace(/\n/g,"")},upcomingInput:function(){var e=this.match;if(e.length<20){e+=this._input.substr(0,20-e.length)}return(e.substr(0,20)+(e.length>20?"...":"")).replace(/\n/g,"")},showPosition:function(){var e=this.pastInput();var t=(new Array(e.length+1)).join("-");return e+this.upcomingInput()+"\n"+t+"^"},test_match:function(e,t){var n,r,i;if(this.options.backtrack_lexer){i={yylineno:this.yylineno,yylloc:{first_line:this.yylloc.first_line,last_line:this.last_line,first_column:this.yylloc.first_column,last_column:this.yylloc.last_column},yytext:this.yytext,match:this.match,matches:this.matches,matched:this.matched,yyleng:this.yyleng,offset:this.offset,_more:this._more,_input:this._input,yy:this.yy,conditionStack:this.conditionStack.slice(0),done:this.done};if(this.options.ranges){i.yylloc.range=this.yylloc.range.slice(0)}}r=e[0].match(/(?:\r\n?|\n).*/g);if(r){this.yylineno+=r.length}this.yylloc={first_line:this.yylloc.last_line,last_line:this.yylineno+1,first_column:this.yylloc.last_column,last_column:r?r[r.length-1].length-r[r.length-1].match(/\r?\n?/)[0].length:this.yylloc.last_column+e[0].length};this.yytext+=e[0];this.match+=e[0];this.matches=e;this.yyleng=this.yytext.length;if(this.options.ranges){this.yylloc.range=[this.offset,this.offset+=this.yyleng]}this._more=false;this._backtrack=false;this._input=this._input.slice(e[0].length);this.matched+=e[0];n=this.performAction.call(this,this.yy,this,t,this.conditionStack[this.conditionStack.length-1]);if(this.done&&this._input){this.done=false}if(n){return n}else if(this._backtrack){for(var s in i){this[s]=i[s]}return false}return false},next:function(){if(this.done){return this.EOF}if(!this._input){this.done=true}var e,t,n,r;if(!this._more){this.yytext="";this.match=""}var i=this._currentRules();for(var s=0;s<i.length;s++){n=this._input.match(this.rules[i[s]]);if(n&&(!t||n[0].length>t[0].length)){t=n;r=s;if(this.options.backtrack_lexer){e=this.test_match(n,i[s]);if(e!==false){return e}else if(this._backtrack){t=false;continue}else{return false}}else if(!this.options.flex){break}}}if(t){e=this.test_match(t,i[r]);if(e!==false){return e}return false}if(this._input===""){return this.EOF}else{return this.parseError("Lexical error on line "+(this.yylineno+1)+". Unrecognized text.\n"+this.showPosition(),{text:"",token:null,line:this.yylineno})}},lex:function(){var t=this.next();if(t){return t}else{return this.lex()}},begin:function(t){this.conditionStack.push(t)},popState:function(){var t=this.conditionStack.length-1;if(t>0){return this.conditionStack.pop()}else{return this.conditionStack[0]}},_currentRules:function(){if(this.conditionStack.length&&this.conditionStack[this.conditionStack.length-1]){return this.conditions[this.conditionStack[this.conditionStack.length-1]].rules}else{return this.conditions["INITIAL"].rules}},topState:function(t){t=this.conditionStack.length-1-Math.abs(t||0);if(t>=0){return this.conditionStack[t]}else{return"INITIAL"}},pushState:function(t){this.begin(t)},stateStackSize:function(){return this.conditionStack.length},options:{},performAction:function(t,n,r,i){var s=i;switch(r){case 0:break;case 1:return 11;break;case 2:return 6;break;case 3:return 8;break;case 4:return 9;break;case 5:return 5;break;case 6:return"INVALID";break}},rules:[/^(?:\s+)/,/^(?:[0-9]+(\.[0-9]+)?\b)/,/^(?:\()/,/^(?:\))/,/^(?:,)/,/^(?:$)/,/^(?:.)/],conditions:{INITIAL:{rules:[0,1,2,3,4,5,6],inclusive:true}}};return e}();e.lexer=t;n.prototype=e;e.Parser=n;return new n}();if(typeof require!=="undefined"&&typeof exports!=="undefined"){exports.parser=parser;exports.Parser=parser.Parser;exports.parse=function(){return parser.parse.apply(parser,arguments)};exports.main=function(t){if(!t[1]){console.log("Usage: "+t[0]+" FILE");process.exit(1)}var n=require("fs").readFileSync(require("path").normalize(t[1]),"utf8");return exports.parser.parse(n)};if(typeof module!=="undefined"&&require.main===module){exports.main(process.argv.slice(1))}}