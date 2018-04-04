#include <stack>
#include <ctype.h>
#include <math.h>
using namespace std;

#define TESTCPPDLL_API __declspec(dllexport)
EXTERN_C __declspec(dllexport) void toPostfixExpression(char* infixExpression, char* strPsExp);
EXTERN_C __declspec(dllexport) int expressionCheck(char* infixExpression);
EXTERN_C __declspec(dllexport) double calculatePex(char* wctPsExp);
