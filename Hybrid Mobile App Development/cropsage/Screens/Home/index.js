import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import List from './List';
import CadastroSolo from './CadastroSolo';

const Stack = createNativeStackNavigator();

const Home = () => {
  return (
      <Stack.Navigator>
        <Stack.Screen name="list" component={List} options={{headerShown: false}} />
        <Stack.Screen name="cadastroSolo" component={CadastroSolo} options={{headerShown: false}} />
      </Stack.Navigator>
  );
};

export default Home;
