import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import PropertyData, {
  Property,
} from 'Components/MDClass/PropertyData/PropertyData';
import { Build } from 'Components/MDClass/BuildData/BuildData';
import { v4 as uuid } from 'uuid';
import PropertyBox from '../Property/PropertyBox';

interface PropertyProps {
  buildValue: Build;
}

export default function BuildPropertyBox({ buildValue }: PropertyProps) {
  const [propertyDatas, setPropertyDatas] = useState<Property[]>(
    buildValue.propertys,
  );

  const handleOnClick = () => {
    const newData = new PropertyData();
    setPropertyDatas((cur) => [...cur, newData]);
    buildValue.propertys = [...buildValue.propertys, new PropertyData()];
  };

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
          {propertyDatas.map((value, index) => {
            return (
              <PropertyBox
                key={uuid()}
                value={value}
                buildValue={buildValue.propertys[index]}
              />
            );
          })}
        </Paper>
      </Box>
    </Box>
  );
}
