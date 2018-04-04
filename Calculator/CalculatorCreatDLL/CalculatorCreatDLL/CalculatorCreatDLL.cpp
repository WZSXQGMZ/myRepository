#include "stdafx.h"
#include "CalculatorCreatDLL.h"

/*
�ú������ڽ���׺���ʽת��Ϊ��׺���ʽ
����
infixExpression:�洢��׺���ʽ���ַ���
strPsExp:��Ҫ�洢��׺���ʽ���ַ���
����ֵΪ�洢��׺���ʽ��ջ
*/
__declspec(dllexport) void toPostfixExpression(char* infixExpression, char* strPsExp) {
	stack<char> tempStack;
	if (!expressionCheck(infixExpression)) {
		return;
	}
	stack<char> postfixExpStack;//���ڴ洢��׺���ʽ��ջ
	stack<char> symbolStack;	//���ڴ洢�������ջ
	symbolStack.push('b');		//������ǰѹ��һ��b��������
	bool isNagetive = false;	//�ж���һ�����Ƿ�Ϊ����
	//�жϵ�һ�����Ƿ�Ϊ����
	if (infixExpression[0] == '-') {
		isNagetive = true;
		infixExpression[0] = '_';
	}
	for (unsigned int i = 0; i < strlen(infixExpression); i++) {
		char currChar = infixExpression[i];
		if (isdigit(currChar) || isNagetive || currChar == '.') {
			int numCount = 0;
			//������ѹ���׺���ʽ����nΪ�ڵ�
			postfixExpStack.push('n');	//n��ʾһ���������Ŀ�ͷ
										//ѹ������
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
		//������������ѹ��symbolStack
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
		else if (currChar == '^' || currChar == '��') {
			symbolStack.push(currChar);
		}
		else if (currChar == 's' || currChar == 'c' || currChar == 't') {
			symbolStack.push(currChar);
			i += 2;	//����s��c��t�ĺ������ַ�
		}
		else if (currChar == 'l') {
			if (infixExpression[i + 1] == 'o') {
				symbolStack.push('o');
				i += 2;	//����l�ĺ������ַ�
			}
			else if (infixExpression[i + 1] == 'n') {
				symbolStack.push('l');
				i++;	//����l�ĺ�һ���ַ�
			}
		}
		else if (currChar == '(') {
			if (infixExpression[i + 1] == '-') {
				isNagetive = true;
				infixExpression[i + 1] = '_';
			}
			symbolStack.push('b');	//ѹ��b����������
		}
		else if (currChar == ')') {
			while (symbolStack.top() != 'b') {
				postfixExpStack.push(symbolStack.top());
				symbolStack.pop();
			}
			symbolStack.pop();
		}
		//��ֹ���ʽĩβû�����ţ�ʹ��symbolStack��ʣ��������û�е���
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
	//������ĺ�׺���ʽѹ��tempStack�������
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
�ú������ڼ����׺���ʽ�Ƿ�Ϸ�
����
infixExpression:�洢��׺���ʽ���ַ���
�Ϸ�����1�����Ϸ�����0
*/
__declspec(dllexport) int expressionCheck(char* infixExpression) {
	stack<int> leftBrakets;
	int decimalPointCount = 0;
	//���жϵ�һ���ַ��Ƿ�Ϸ�
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
		}	//�ж���������ƥ��
		else if (isdigit(infixExpression[i])) {
			if (!isdigit(infixExpression[i + 1]) && infixExpression[i + 1] != '.' && infixExpression[i + 1] != '*' && infixExpression[i + 1] != '/'&&infixExpression[i + 1] != '+' && infixExpression[i + 1] != '-' && infixExpression[i + 1] != '^' && infixExpression[i + 1] != '\0' && infixExpression[i + 1] != ')') {
				return 0;
			}
		}
		else if ((infixExpression[i] == '*' || infixExpression[i] == '/' || infixExpression[i] == '+' || infixExpression[i] == '-' || infixExpression[i] == '^' || infixExpression[i] == '��') &&
			(!isdigit(infixExpression[i + 1]) && infixExpression[i + 1] != '(' && infixExpression[i + 1] != 'l' && infixExpression[i + 1] != 's' && infixExpression[i + 1] != 'c' && infixExpression[i + 1] != 't')) {
			return 0;
		}	//�ж������λ����ȷ
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
		}	//�ж�ln��log
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
		}	//�ж�sin
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
		}	//�ж�cos
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
		//�ж�С�����Ƿ�ƥ��
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
		}	//�ж�С�����Ƿ�ƥ�����

		if (infixExpression[i] == '(' && !isdigit(infixExpression[i + 1]) && infixExpression[i + 1] != '-' && infixExpression[i + 1] != 's' && infixExpression[i + 1] != 'c' && infixExpression[i + 1] != 't' && infixExpression[i + 1] != 'l') {
			return 0;
		}	//�жϸ���λ����ȷ
		else if (infixExpression[i] == '(' && infixExpression[i + 1] == ')') {
			return 0;
		}	//�ж�"()"�����	
	}

	return 1;
}
/*
�ú������ں�׺���ʽ�ļ���
����
strPsExp:�洢��׺���ʽ��ջ
����ֵΪ������
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
		else if (currChar == '��') {
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