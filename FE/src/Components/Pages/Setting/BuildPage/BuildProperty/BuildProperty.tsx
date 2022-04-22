import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import PropertyData from 'MDClass/PropertyData/PropertyData';
import { v4 as uuid } from 'uuid';
import Property from './Property/Property';

// interface PropertyData {
//   name: string;
//   data: string[];
// }

export default function BuildProperty() {
  const [propertyData, setPropertyData] = useState<PropertyData[]>([]);

  const handleOnClick = () => {
    const newData = new PropertyData();
    setPropertyData((cur) => [...cur, newData]);
  };

  useEffect(() => {
    const newData = new PropertyData();
    setPropertyData([newData]);
  }, []);

  return (
    <Box my={3}>
      <Box position="relative" sx={{ top: 10, left: 10 }}>
        <Paper sx={{ padding: 1, textAlign: 'center', width: 120 }}>
          Property
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3 }}>
          <Box mb={3}>
            <Button
              variant="outlined"
              startIcon={<AddIcon />}
              onClick={handleOnClick}
            >
              Property Add
            </Button>
          </Box>
          {propertyData.map(() => {
            return <Property key={uuid()} />;
          })}
        </Paper>
      </Box>
    </Box>
  );
}
