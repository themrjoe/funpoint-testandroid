#include <iostream>
using namespace std;

int main() {
    int x = 0; 
    int y = 0;

    if(x++ && y++) {
        y+=2;
    }

    cout << y+x<<endl;
    return 0;
}