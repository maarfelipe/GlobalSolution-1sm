import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import List from './List';

const Stack = createNativeStackNavigator();

const Home = () => {
  return (
      <Stack.Navigator>
        <Stack.Screen name="list" component={List} options={{headerTitle: "List"}} />
      </Stack.Navigator>
  );
};

export default Home;
