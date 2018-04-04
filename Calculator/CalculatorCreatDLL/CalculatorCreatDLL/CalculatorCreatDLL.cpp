#include "stdafx.h"
#include "CalculatorCreatDLL.h"

/*
该函数用于将中缀表达式转换为后缀表达式
参数
infixExpression:存储中缀表达式的字符串
strPsExp:将要存储后缀表达式的字符串
返回值为存储后缀表达式的栈
*/
__declspec(dllexport) void toPostfixExpression(char* infixExpression, char* strPsExp) {
	stack<char> tempStack;
	if (!expressionCheck(infixExpression)) {
		return;
	}
	stack<char> postfixExpStack;//用于存储后缀表达式的栈
	stack<char> symbolStack;	//用于存储运算符的栈
	symbolStack.push('b');		//先在最前压入一个b当作括号
	bool isNagetive = false;	//判断下一个数是否为负数
	//判断第一个数是否为负数
	if (infixExpression[0] == '-') {
		isNagetive = true;
		infixExpression[0] = '_';
	}
	for (unsigned int i = 0; i < strlen(infixExpression); i++) {
		char currChar = infixExpression[i];
		if (isdigit(currChar) || isNagetive || currChar == '.') {
			int numCount = 0;
			//将数字压入后缀表达式，以n为节点
			postfixExpStack.push('n');	//n表示一个浮点数的开头
										//压入数字
			while (isdigit(infixExpression[i]) || infixExpression[i] == '.' || isNagetive) {
				if (currChar == '_') {
					isNagetive = false;
				}
				currChar = infixExpression[i];
				postfixExpStack.push(currChar);
				numCount++;
				i++;
			}
			i--;
		}
		//如果是运算符则压入symbolStack
		else if (currChar == '+' || currChar == '-') {
			while (true) {
				if (symbolStack.top() != 'b') {
					postfixExpStack.push(symbolStack.top());
					symbolStack.pop();
				}
				else {
					symbolStack.push(currChar);
					break;
				}
			}
		}
		else if (currChar == '*' || currChar == '/') {
			while (true) {
				if (symbolStack.top() != 'b' &&symbolStack.top() != '+'&&symbolStack.top() != '-') {
					postfixExpStack.push(symbolStack.top());
					symbolStack.pop();
				}
				else {
					symbolStack.push(currChar);
					break;
				}
			}
		}
		else if (currChar == '^' || currChar == '√') {
			symbolStack.push(currChar);
		}
		else if (currChar == 's' || currChar == 'c' || currChar == 't') {
			symbolStack.push(currChar);
			i += 2;	//跳过s、c、t的后两个字符
		}
		else if (currChar == 'l') {
			if (infixExpression[i + 1] == 'o') {
				symbolStack.push('o');
				i += 2;	//跳过l的后两个字符
			}
			else if (infixExpression[i + 1] == 'n') {
				symbolStack.push('l');
				i++;	//跳过l的后一个字符
			}
		}
		else if (currChar == '(') {
			if (infixExpression[i + 1] == '-') {
				isNagetive = true;
				infixExpression[i + 1] = '_';
			}
			symbolStack.push('b');	//压入b代表左括号
		}
		else if (currChar == ')') {
			while (symbolStack.top() != 'b') {
				postfixExpStack.push(symbolStack.top());
				symbolStack.pop();
			}
			symbolStack.pop();
		}
		//防止表达式末尾没有括号，使得symbolStack中剩余的运算符没有弹出
		if (i == (strlen(infixExpression) - 1) && !symbolStack.empty()) {
			char temp = symbolStack.top();
			symbolStack.pop();
			while (temp != 'b') {
				postfixExpStack.push(temp);
				temp = symbolStack.top();
				symbolStack.pop();
			}
		}
	}
	//将倒序的后缀表达式压入tempStack变成正序
	while (!postfixExpStack.empty()) {
		tempStack.push(postfixExpStack.top());
		postfixExpStack.pop();
	}
	int i = 0;
	while (!tempStack.empty()) {
		strPsExp[i] = tempStack.top();
		tempStack.pop();
		i++;
	}
	strPsExp[i] = '\0';

	return;
}
/*
该函数用于检查中缀表达式是否合法
参数
infixExpression:存储中缀表达式的字符串
合法返回1，不合法返回0
*/
__declspec(dllexport) int expressionCheck(char* infixExpression) {
	stack<int> leftBrakets;
	int decimalPointCount = 0;
	//先判断第一个字符是否合法
	if (infixExpression[0] != '-' && infixExpression[0] != 's'&&infixExpression[0] != 'c'&&infixExpression[0] != 't'&&infixExpression[0] != 'l'&&infixExpression[0] != '('&&!isdigit(infixExpression[0])) {
		return false;
	}
	for (unsigned int i = 0; infixExpression[i] != '\0'; i++) {
		if (infixExpression[i] == '(') {
			leftBrakets.push(i);
		}
		else if (infixExpression[i] == ')') {
			if (leftBrakets.empty()) {
				return 0;
			}
			else {
				leftBrakets.pop();
			}
		}	//判断左右括号匹配
		else if (isdigit(infixExpression[i])) {
			if (!isdigit(infixExpression[i + 1]) && infixExpression[i + 1] != '.' && infixExpression[i + 1] != '*' && infixExpression[i + 1] != '/'&&infixExpression[i + 1] != '+' && infixExpression[i + 1] != '-' && infixExpression[i + 1] != '^' && infixExpression[i + 1] != '\0' && infixExpression[i + 1] != ')') {
				return 0;
			}
		}
		else if ((infixExpression[i] == '*' || infixExpression[i] == '/' || infixExpression[i] == '+' || infixExpression[i] == '-' || infixExpression[i] == '^' || infixExpression[i] == '√') &&
			(!isdigit(infixExpression[i + 1]) && infixExpression[i + 1] != '(' && infixExpression[i + 1] != 'l' && infixExpression[i + 1] != 's' && infixExpression[i + 1] != 'c' && infixExpression[i + 1] != 't')) {
			return 0;
		}	//判断运算符位置正确
		else if (infixExpression[i] == 'l') {
			if (infixExpression[++i] != 'n') {
				if (infixExpression[i] != 'o') {
					return 0;
				}
				else if (infixExpression[++i] != 'g') {
					return 0;
				}
				else if (!isdigit(infixExpression[i + 1]) && infixExpression[i + 1] != '(') {
					return 0;
				}
			}
		}	//判断ln和log
		else if (infixExpression[i] == 's') {
			if (infixExpression[++i] != 'i') {
				return 0;
			}
			else if (infixExpression[++i] != 'n') {
				return 0;
			}
			else if (!isdigit(infixExpression[i + 1]) && infixExpression[i + 1] != '(') {
				return 0;
			}
		}	//判断sin
		else if (infixExpression[i] == 'c') {
			if (infixExpression[++i] != 'o') {
				return 0;
			}
			else if (infixExpression[++i] != 's') {
				return 0;
			}
			else if (!isdigit(infixExpression[i + 1]) && infixExpression[i + 1] != '(') {
				return 0;
			}
		}	//判断cos
		else if (infixExpression[i] == 't') {
			if (infixExpression[++i] != 'a') {
				return 0;
			}
			else if (infixExpression[++i] != 'n') {
				return 0;
			}
			else if (!isdigit(infixExpression[i + 1]) && infixExpression[i + 1] != '(') {
				return 0;
			}
		}
		//判断小数点是否匹配
		else if (!isdigit(infixExpression[i]) && infixExpression[i + 1] == '.') {
			return 0;
		}
		else if (isdigit(infixExpression[i]) && infixExpression[i + 1] == '.') {
			if (decimalPointCount == 1) {
				return 0;
			}
			decimalPointCount = 1;
		}
		else if (infixExpression[i] == '.' && !isdigit(infixExpression[i + 1])) {
			return 0;
		}
		else if (isdigit(infixExpression[i]) && !isdigit(infixExpression[i + 1])) {
			decimalPointCount = 0;
		}	//判断小数点是否匹配结束

		if (infixExpression[i] == '(' && !isdigit(infixExpression[i + 1]) && infixExpression[i + 1] != '-' && infixExpression[i + 1] != 's' && infixExpression[i + 1] != 'c' && infixExpression[i + 1] != 't' && infixExpression[i + 1] != 'l') {
			return 0;
		}	//判断负号位置正确
		else if (infixExpression[i] == '(' && infixExpression[i + 1] == ')') {
			return 0;
		}	//判断"()"的情况	
	}

	return 1;
}
/*
该函数用于后缀表达式的计算
参数
strPsExp:存储后缀表达式的栈
返回值为计算结果
*/
__declspec(dllexport) double calculatePex(char* strPsExp) {
	stack<char> postfixExpStack;
	for (int i = strlen(strPsExp) - 1; i >= 0; i--) {
		postfixExpStack.push(strPsExp[i]);
	}
	stack<double> tempNumStack;
	//double result;
	while (!postfixExpStack.empty()) {
		double value1, value2;
		char tempNum[11];
		char currChar = postfixExpStack.top();
		postfixExpStack.pop();
		if (currChar == 'n') {
			int count = 0;
			while (true) {
				currChar = postfixExpStack.top();
				postfixExpStack.pop();
				if (currChar == '_') {
					tempNum[count] = '-';
				}
				else {
					tempNum[count] = currChar;
				}
				count++;
				if (postfixExpStack.empty() || (!isdigit(postfixExpStack.top()) && postfixExpStack.top() != '.')) {
					break;
				}
			}
			tempNum[count] = '\0';
			tempNumStack.push(atof(tempNum));
		}
		else if (currChar == '+') {
			value1 = tempNumStack.top();
			tempNumStack.pop();
			value2 = tempNumStack.top();
			tempNumStack.pop();
			tempNumStack.push(value2 + value1);
		}
		else if (currChar == '-') {
			value1 = tempNumStack.top();
			tempNumStack.pop();
			value2 = tempNumStack.top();
			tempNumStack.pop();
			tempNumStack.push(value2 - value1);
		}
		else if (currChar == '*') {
			value1 = tempNumStack.top();
			tempNumStack.pop();
			value2 = tempNumStack.top();
			tempNumStack.pop();
			tempNumStack.push(value2 * value1);
		}
		else if (currChar == '/') {
			value1 = tempNumStack.top();
			tempNumStack.pop();
			value2 = tempNumStack.top();
			tempNumStack.pop();
			tempNumStack.push(value2 / value1);
		}
		else if (currChar == '^') {
			value1 = tempNumStack.top();
			tempNumStack.pop();
			value2 = tempNumStack.top();
			tempNumStack.pop();
			tempNumStack.push(pow(value2, value1));
		}
		else if (currChar == '√') {
			value2 = tempNumStack.top();
			tempNumStack.pop();
			tempNumStack.push(sqrt(value2));
		}
		else if (currChar == 's') {
			value2 = tempNumStack.top();
			tempNumStack.pop();
			tempNumStack.push(sin(value2));
		}
		else if (currChar == 'c') {
			value2 = tempNumStack.top();
			tempNumStack.pop();
			tempNumStack.push(cos(value2));
		}
		else if (currChar == 't') {
			value2 = tempNumStack.top();
			tempNumStack.pop();
			tempNumStack.push(tan(value2));
		}
		else if (currChar == 'l') {
			value2 = tempNumStack.top();
			tempNumStack.pop();
			tempNumStack.push(log(value2));
		}
		else if (currChar == 'o') {
			value2 = tempNumStack.top();
			tempNumStack.pop();
			tempNumStack.push(log2(value2));
		}
	}

	return tempNumStack.top();
}