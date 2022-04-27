import React from 'react';
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepButton from '@mui/material/StepButton';
import Button from '@mui/material/Button';
import TabContext from '@mui/lab/TabContext';
import TabPanel from '@mui/lab/TabPanel';
import Paper from '@mui/material/Paper';
import BuildPage from './BuildPage/BuildPage';
import GitLabPage from './GitLabPage/GitLabPage';
import AxiosPage from './AxiosPage/AxiosPage';

const steps = ['Build Settings', 'GitLab Setting', 'Make Project'];

export default function Setting() {
  const [activeStep, setActiveStep] = React.useState(0);
  const [completed, setCompleted] = React.useState<{
    [k: number]: boolean;
  }>({});

  const totalSteps = () => {
    return steps.length;
  };

  const completedSteps = () => {
    return Object.keys(completed).length;
  };

  const isLastStep = () => {
    return activeStep === totalSteps() - 1;
  };

  const allStepsCompleted = () => {
    return completedSteps() === totalSteps();
  };

  const handleNext = () => {
    const newActiveStep =
      isLastStep() && !allStepsCompleted()
        ? // It's the last step, but not all steps have been completed,
          // find the first step that has been completed
          steps.findIndex((step, i) => !(i in completed))
        : activeStep + 1;
    setActiveStep(newActiveStep);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  const handleStep = (step: number) => () => {
    setActiveStep(step);
  };

  // const handleComplete = () => {
  //   const newCompleted = completed;
  //   newCompleted[activeStep] = true;
  //   setCompleted(newCompleted);
  //   handleNext();
  // };

  const handleReset = () => {
    setActiveStep(0);
    setCompleted({});
  };

  return (
    <Box mt={3} sx={{ width: '100%' }}>
      <Paper sx={{ borderRadius: 3 }}>
        <Stepper
          nonLinear
          activeStep={activeStep}
          alternativeLabel
          sx={{ pt: 3 }}
        >
          {steps.map((label, index) => (
            <Step key={label} completed={completed[index]}>
              <StepButton color="inherit" onClick={handleStep(index)}>
                {label}
              </StepButton>
            </Step>
          ))}
        </Stepper>
        <Box>
          {allStepsCompleted() ? (
            <Box sx={{ display: 'flex', flexDirection: 'row', pt: 2 }}>
              <Box sx={{ flex: '1 1 auto' }} />
              <Button onClick={handleReset}>Reset</Button>
            </Box>
          ) : (
            <>
              <Box sx={{ display: 'flex', flexDirection: 'row', pt: 2 }}>
                <Button
                  color="inherit"
                  disabled={activeStep === 0}
                  onClick={handleBack}
                  sx={{ mr: 1 }}
                >
                  Back
                </Button>
                <Box sx={{ flex: '1 1 auto' }} />
                <Button onClick={handleNext} sx={{ mr: 1 }}>
                  Next
                </Button>
              </Box>
              <TabContext value={String(activeStep)}>
                <Box sx={{ mx: 3, borderBottom: 1, borderColor: 'divider' }} />
                <TabPanel value="0">
                  <BuildPage />
                </TabPanel>
                <TabPanel value="1">
                  <GitLabPage />
                </TabPanel>
                <TabPanel value="2">
                  <AxiosPage />
                </TabPanel>
              </TabContext>
            </>
          )}
        </Box>
      </Paper>
    </Box>
  );
}
