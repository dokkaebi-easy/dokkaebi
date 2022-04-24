import { BrowserRouter, Switch, Route } from 'react-router-dom';
import BasicLayout from 'Components/Layouts/BasicLayout/BasicLayout';
import CssBaseline from '@mui/material/CssBaseline';

function App() {
  return (
    <>
      <CssBaseline />
      <Switch>
        <Route path="/">
          <BasicLayout />
        </Route>
      </Switch>
    </>
  );
}

export default App;
